package com.flamelab.shopserver.dtos.create;

import com.flamelab.shopserver.utiles.data.ObjectWithData;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class CommonCreateDto extends ObjectWithData {
}
