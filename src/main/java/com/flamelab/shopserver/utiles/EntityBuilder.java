package com.flamelab.shopserver.utiles;


import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.entities.CommonEntity;

public interface EntityBuilder<E extends CommonEntity, C extends CommonCreateDto> {

    E buildEntityFromDto(C createDto, Class<C> createDtoClass, Class<E> entityClass);

}
