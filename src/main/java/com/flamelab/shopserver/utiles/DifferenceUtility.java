package com.flamelab.shopserver.utiles;

import com.flamelab.shopserver.utiles.data.ObjectWithData;
import com.flamelab.shopserver.utiles.naming.FieldNames;

import java.util.Map;

public interface DifferenceUtility<B extends ObjectWithData> {

    Map<FieldNames, Object> getChanges(B objectBeforeChanges, B updatedObject, Class<B> targetClass);

}
