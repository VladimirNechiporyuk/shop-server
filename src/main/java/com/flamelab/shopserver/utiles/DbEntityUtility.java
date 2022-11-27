package com.flamelab.shopserver.utiles;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.dtos.update.CommonUpdateDto;
import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.utiles.data.SideOfValue;
import com.flamelab.shopserver.utiles.naming.DbCollectionNames;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DbEntityUtility<E extends CommonEntity, C extends CommonCreateDto, U extends CommonUpdateDto> {

    E saveEntity(C createDto, Class<C> createDtoClass, Class<E> targetClass, DbCollectionNames dbCollectionName);

    E findOneByOrThrow(Map<FieldNames, Object> criterias, Class<E> searchedClass, DbCollectionNames dbCollectionName);

    E findOneBy(Map<FieldNames, Object> criterias, Class<E> searchedClass, DbCollectionNames dbCollectionName);

    List<E> findAllBy(Map<FieldNames, Object> criterias, Class<E> searchedClass, DbCollectionNames dbCollectionName);

    List<E> findAllWhichContainsAnyOfCriteria(Map<FieldNames, Object> criterias, List<Class<E>> searchedClasses, DbCollectionNames dbCollectionName);

    List<E> findAllWhichContainsPartOfParameterFromTheSide(Map<FieldNames, Object> criterias, SideOfValue side, Class<E> searchedClass, DbCollectionNames dbCollectionName);

    List<E> findAllByClass(Class<E> entityClass, DbCollectionNames dbCollectionName);

    List<E> findAllInCollection(List<Class<E>> entityClassList, DbCollectionNames dbCollectionName);

    E updateEntity(Map<FieldNames, Object> criterias, U updatedDto, Class<U> updateDtoClass, Class<E> entityClass, DbCollectionNames dbCollectionName);

    boolean isDbEntityListParameterContainsValue(ObjectId entityId, FieldNames entityFieldListName, Object searchedValue, Class<E> targetClass, DbCollectionNames dbCollectionName);

    boolean isEntityExistsBy(Map<FieldNames, Object> criterias, Class<E> targetClass, DbCollectionNames dbCollectionName);

    void deleteEntityBy(Map<FieldNames, Object> criterias, Class<E> targetClass, DbCollectionNames dbCollectionName);

}
