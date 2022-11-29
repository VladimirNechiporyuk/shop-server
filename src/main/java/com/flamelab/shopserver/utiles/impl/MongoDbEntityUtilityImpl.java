package com.flamelab.shopserver.utiles.impl;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.dtos.update.CommonUpdateDto;
import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.exceptions.MoreThanOneEntityExistsByQueryException;
import com.flamelab.shopserver.exceptions.NoExistentEntityException;
import com.flamelab.shopserver.exceptions.WrongCriteriaNameProvidedException;
import com.flamelab.shopserver.utiles.ClassUtility;
import com.flamelab.shopserver.utiles.DbEntityUtility;
import com.flamelab.shopserver.utiles.PrepareDataBeforeDbAction;
import com.flamelab.shopserver.utiles.data.SideOfValue;
import com.flamelab.shopserver.utiles.naming.DbCollectionNames;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.flamelab.shopserver.utiles.naming.FieldNames.EMAIL__FIELD_APPELLATION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.ID__FIELD_APPELLATION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.getFieldAppellation;

@Component
@RequiredArgsConstructor
@Slf4j
public class MongoDbEntityUtilityImpl<E extends CommonEntity, C extends CommonCreateDto, U extends CommonUpdateDto> implements DbEntityUtility<E, C, U> {

    private final MongoTemplate mongoTemplate;
    private final ClassUtility<E> classUtility;
    private final PrepareDataBeforeDbAction<E, C, U> prepareDataBeforeDbAction;
    private final String separateSymbol = ",";

    public E saveEntity(C createDto, Class<C> createDtoClass, Class<E> targetClass, DbCollectionNames dbCollectionName) {
        return mongoTemplate.save(
                prepareDataBeforeDbAction.prepareNewEntity(createDto, createDtoClass, targetClass),
                dbCollectionName.getCollection());
    }

    public E findOneByOrThrow(Map<FieldNames, Object> criterias, Class<E> searchedClass, DbCollectionNames dbCollectionName) {
        E entity;
        try {
            entity = findOneBy(criterias, searchedClass, dbCollectionName);
        } catch (MoreThanOneEntityExistsByQueryException e) {
            throw new MoreThanOneEntityExistsByQueryException(String.format(
                    "More than one entity found by query with criterias: %s." +
                            "\nPlease try to find needed entity using id or several criterias", parseCriterias(criterias)));
        }
        if (entity == null) {
            throw new NoExistentEntityException(String.format("No entity found in collection '%s' by class %s and criterias %s.",
                    dbCollectionName, searchedClass, parseCriterias(criterias)));
        }
        return entity;
    }

    public E findOneBy(Map<FieldNames, Object> criterias, Class<E> searchedClass, DbCollectionNames dbCollectionName) {
        Query query = buildQuery(criterias);
        List<E> entitiesFoundFromDb = mongoTemplate.find(query, searchedClass, dbCollectionName.getCollection());
        if (entitiesFoundFromDb.size() == 1) {
            return entitiesFoundFromDb.stream().findFirst().get();
        } else if (entitiesFoundFromDb.size() > 1) {
            throw new MoreThanOneEntityExistsByQueryException("More then one entity found");
        } else {
            throw new NoExistentEntityException("No existing entities found");
        }
    }

    public List<E> findAllBy(Map<FieldNames, Object> criterias, Class<E> searchedClass, DbCollectionNames dbCollectionName) {
        return mongoTemplate.find(buildQuery(criterias), searchedClass, dbCollectionName.getCollection());
    }

    public List<E> findAllWhichContainsAnyOfCriteria(Map<FieldNames, Object> criterias, List<Class<E>> searchedClasses, DbCollectionNames dbCollectionName) {
        List<E> entities = new ArrayList<>();
        for (Map.Entry<FieldNames, Object> entry : criterias.entrySet()) {
            if (entry.getValue() == null) {
                log.error("No provided value for criteria {}", entry.getKey());
            } else {
                List<E> allInCollection = findAllInCollection(searchedClasses, dbCollectionName);
                for (Class<E> targetClass : searchedClasses) {
                    if (classUtility.isParameterAsList(entry.getValue())) {
                        Object[] criteriaValues = String.valueOf(entry.getValue()).split(separateSymbol);
                        for (Object criteriaValue : criteriaValues) {
                            entities.addAll(searchEntitiesByCriteria(entry.getKey(), String.valueOf(criteriaValue), targetClass, allInCollection));
                        }
                    }
                    entities.addAll(searchEntitiesByCriteria(entry.getKey(), String.valueOf(entry.getValue()), targetClass, allInCollection));
                }
            }
        }
        return entities;
    }

    public List<E> findAllWhichContainsPartOfParameterFromTheSide(Map<FieldNames, Object> criterias, SideOfValue side, Class<E> searchedClass, DbCollectionNames dbCollectionName) {
        return mongoTemplate.find(buildQueryWithAnyValueFromSide(criterias, side), searchedClass, dbCollectionName.getCollection());
    }

    public List<E> findAllByClass(Class<E> entityClass, DbCollectionNames dbCollectionName) {
        return mongoTemplate.findAll(entityClass, dbCollectionName.getCollection());
    }

    public List<E> findAllInCollection(List<Class<E>> entityClassList, DbCollectionNames dbCollectionName) {
        List<E> resultList = new ArrayList<>();
        for (Class<E> entityClass : entityClassList) {
            resultList.addAll(findAllByClass(entityClass, dbCollectionName));
        }
        return resultList;
    }

    public E updateEntity(Map<FieldNames, Object> criterias, U updatedDto, Class<U> updateDtoClass, Class<E> entityClass, DbCollectionNames dbCollectionName) {
        Query searchQuery = buildQuery(criterias);
        Map<FieldNames, Object> changes =
                prepareDataBeforeDbAction.prepareChanges(
                        findOneBy(criterias, entityClass, dbCollectionName),
                        updatedDto,
                        updateDtoClass,
                        entityClass);
        Update update = new Update();
        changes.put(EMAIL__FIELD_APPELLATION, "changed");
        for (Map.Entry<FieldNames, Object> entry : changes.entrySet()) {
            update.set(entry.getKey().getField(), entry.getValue());
        }
        mongoTemplate.findAndModify(
                searchQuery,
                update,
                entityClass,
                dbCollectionName.getCollection());
        return findOneBy(criterias, entityClass, dbCollectionName);
    }

    public boolean isDbEntityListParameterContainsValue(ObjectId entityId, FieldNames entityFieldListName, Object searchedValue, Class<E> targetClass, DbCollectionNames dbCollectionName) {
        E entityFromDb = findOneBy(Map.of(ID__FIELD_APPELLATION, entityId), targetClass, dbCollectionName);
        return isListContainsValue(entityFieldListName, targetClass, entityFromDb, searchedValue);
    }

    public boolean isEntityExistsBy(Map<FieldNames, Object> criterias, Class<E> targetClass, DbCollectionNames dbCollectionName) {
        return mongoTemplate.exists(buildQuery(criterias), targetClass, dbCollectionName.getCollection());
    }

    @Override
    public void deleteEntityBy(Map<FieldNames, Object> criterias, Class<E> targetClass, DbCollectionNames dbCollectionName) {
        findOneBy(criterias, targetClass, dbCollectionName);
        mongoTemplate.remove(buildQuery(criterias), targetClass, dbCollectionName.getCollection());
    }

    private List<E> searchEntitiesByCriteria(FieldNames fieldName, String criteriaValue, Class<E> targetClass, List<E> allInCollection) {
        return allInCollection.stream()
                .filter(object ->
                        classUtility.isFieldValueEqualsToCriteria(
                                classUtility.findFieldFromClass(fieldName, targetClass),
                                criteriaValue,
                                object))
                .collect(Collectors.toList());
    }

    private boolean isListContainsValue(FieldNames entityFieldListName, Class<E> targetClass, E entityFromDb, Object searchedValue) {
        Field entityCollectionField = classUtility.findFieldFromClass(entityFieldListName, targetClass);
        List dbEntityParameterList;
        try {
            dbEntityParameterList = (List) entityCollectionField.get(entityFromDb);
            if (classUtility.isParameterAsList(dbEntityParameterList)) {
                throw new RuntimeException(String.format(String.format("Parameter '%s' is not a list", entityFieldListName)));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Something went wrong during getting values of list from db entity '%s' by field '%s'", entityFromDb, entityFieldListName));
        }
        return dbEntityParameterList.contains(searchedValue);
    }

    private Query buildQuery(Map<FieldNames, Object> criterias) {
        Query query = new Query();
        for (Map.Entry<FieldNames, Object> entry : criterias.entrySet()) {
            if (classUtility.isParameterAsList(entry.getValue())) {
                List<Object> values = Arrays.asList(String.valueOf(entry.getValue()).split(separateSymbol));
                values.forEach(value -> addCriteriaToQuery(entry.getKey(), value, query));
            } else {
                query.addCriteria(Criteria.where(entry.getKey().getField()).is(entry.getValue()));
            }
        }
        return query;
    }

    private Query buildQueryWithAnyValueFromSide(Map<FieldNames, Object> criterias, SideOfValue sideOfValue) {
        Query query = new Query();
        for (Map.Entry<FieldNames, Object> entry : criterias.entrySet()) {
            if (classUtility.isParameterAsList(entry.getValue())) {
                List<Object> values = Arrays.asList(String.valueOf(entry.getValue()).split(separateSymbol));
                switch (sideOfValue) {
                    case BOTH ->
                            values.forEach(value -> addCriteriaAndAnyValueFromBothSides(entry.getKey(), value, query));
                    case RIGHT ->
                            values.forEach(value -> addCriteriaAndAnyValueFromRightSide(entry.getKey(), value, query));
                    case LEFT ->
                            values.forEach(value -> addCriteriaAndAnyValueFromLeftSide(entry.getKey(), value, query));
                }
            }
            query.addCriteria(Criteria.where(String.valueOf(entry.getKey().getField())).is(entry.getValue()));
        }
        return query;
    }

    private void addCriteriaToQuery(FieldNames fieldName, Object value, Query query) {
        query.addCriteria(Criteria.where(fieldName.getField()).is(value));
    }

    private void addCriteriaAndAnyValueFromBothSides(FieldNames fieldName, Object value, Query query) {
        query.addCriteria(Criteria.where(fieldName.getField()).is("/" + value + "/"));
    }

    private void addCriteriaAndAnyValueFromRightSide(FieldNames fieldName, Object value, Query query) {
        query.addCriteria(Criteria.where(fieldName.getField()).is("^" + value + "/"));
    }

    private void addCriteriaAndAnyValueFromLeftSide(FieldNames fiendName, Object value, Query query) {
        query.addCriteria(Criteria.where(fiendName.getField()).is("/" + value + "$"));
    }

    private Map<String, Object> parseCriterias(Map<FieldNames, Object> criterias) {
        return criterias.entrySet().stream()
                .collect(Collectors.toMap(c -> c.getKey().getField(), Map.Entry::getValue));
    }

}
