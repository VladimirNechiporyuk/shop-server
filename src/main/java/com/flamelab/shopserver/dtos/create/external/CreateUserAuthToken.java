package com.flamelab.shopserver.dtos.create.external;

import com.flamelab.shopserver.dtos.create.CommonCreateDto;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CreateUserAuthToken extends CommonCreateDto {

    private String email;
    private String password;

}
