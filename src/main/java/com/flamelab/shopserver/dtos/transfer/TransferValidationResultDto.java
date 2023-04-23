package com.flamelab.shopserver.dtos.transfer;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransferValidationResultDto extends TransferCommonDto{

    private boolean validationResult;

}
