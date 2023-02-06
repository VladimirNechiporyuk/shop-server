package com.flamelab.shopserver.dtos.transafer;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TransferAuthTokenDto extends CommonTransferDto {

    private final String token_type = "bearer";
    private String token;

}
