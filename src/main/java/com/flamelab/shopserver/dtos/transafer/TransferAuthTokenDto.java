package com.flamelab.shopserver.dtos.transafer;

import com.flamelab.shopserver.enums.Roles;
import lombok.*;

import static com.flamelab.shopserver.enums.AuthTokenType.BEARER;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TransferAuthTokenDto extends CommonTransferDto {

    private String userId;
    private final String token_type = BEARER.getTypeName();
    private String token;
    private String email;
    private Roles role;

}
