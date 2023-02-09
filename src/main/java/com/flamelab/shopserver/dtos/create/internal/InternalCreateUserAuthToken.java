package com.flamelab.shopserver.dtos.create.internal;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.enums.AuthTokenType;
import com.flamelab.shopserver.enums.Roles;
import lombok.*;
import org.bson.types.ObjectId;

import static com.flamelab.shopserver.enums.AuthTokenType.BEARER;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class InternalCreateUserAuthToken extends CommonCreateDto {

    private String userId;
    private final String tokenType = BEARER.getTypeName();
    private String token;
    private String email;
    private Roles role;
    private Integer usageAmount;

}
