package com.flamelab.shopserver.utiles;

import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.utiles.naming.DbCollectionNames;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.bson.types.ObjectId;

public interface DbEntityUtilityBySeveralCollections<F extends CommonEntity, S extends CommonEntity> {

    F findFirstEntityWhichParameterLocatedInSecondDbEntityListParameter(ObjectId firstEntityId,
                                                                        Object firstEntitySearchedValue,
                                                                        Class<F> firstEntityClass,
                                                                        DbCollectionNames firstEntityDbCollectionName,
                                                                        ObjectId secondEntityId,
                                                                        FieldNames secondEntityFieldListName,
                                                                        Class<S> secondEntityClass,
                                                                        DbCollectionNames secondEntityDbCollectionName);

}
