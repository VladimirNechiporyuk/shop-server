package com.flamelab.shopserver.dtos.create.internal;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.enums.Roles;
import lombok.*;
import org.bson.types.ObjectId;

import static com.flamelab.shopserver.enums.AuthTokenType.BEARER;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class InternalCreateShop extends CommonCreateDto {

    private String name;
    private ObjectId ownerId;

}
