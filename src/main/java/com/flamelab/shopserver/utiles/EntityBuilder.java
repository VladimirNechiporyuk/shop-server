package com.flamelab.shopserver.utiles;


import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.entities.CommonEntity;

public interface EntityBuilder<E extends CommonEntity, D extends CommonCreateDto> {

    E buildEntityFromDto(D createDto, Class<D> createDtoClass, Class<E> entityClass);

}
