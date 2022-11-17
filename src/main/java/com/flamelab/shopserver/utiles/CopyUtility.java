package com.flamelab.shopserver.utiles;

import com.flamelab.shopserver.utiles.data.ObjectWithData;

public interface CopyUtility<C extends ObjectWithData> {

    C copy(C original, Class<C> copiedClass);

}
