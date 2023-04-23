package com.flamelab.shopserver.dtos.create.wallet_operations;

import com.flamelab.shopserver.dtos.create.CreateCommonDto;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserWithdrawData extends CreateCommonDto {

    private double value;
    private String pan;
    private String userPassword;

}
