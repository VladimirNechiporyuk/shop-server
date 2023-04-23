package com.flamelab.shopserver.dtos.transfer;

import com.flamelab.shopserver.enums.Roles;
import lombok.*;

import java.util.UUID;

import static com.flamelab.shopserver.enums.AuthTokenType.BEARER;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransferAuthTokenDto extends TransferCommonDto {

    private String userId;
    private final String token_type = BEARER.getTypeName();
    private String token;
    private String email;
    private Roles role;

}
