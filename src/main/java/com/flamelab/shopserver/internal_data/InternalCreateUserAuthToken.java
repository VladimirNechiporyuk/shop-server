package com.flamelab.shopserver.internal_data;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import com.flamelab.shopserver.enums.Roles;
import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class InternalCreateUserAuthToken extends CommonCreateDto {

    private ObjectId token;
    private String email;
    private Roles role;
    private Integer usageAmount;

}
