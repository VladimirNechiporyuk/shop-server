package com.flamelab.shopserver.internal_data;

import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.utiles.data.ObjectWithData;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends ObjectWithData {

    private ProductName name;
    private double price;
    private int amount;

}
