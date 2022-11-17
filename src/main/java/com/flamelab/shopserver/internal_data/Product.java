package com.flamelab.shopserver.internal_data;

import com.flamelab.shopserver.enums.ProductName;
import lombok.*;

@Data
@AllArgsConstructor
public class Product {

    private ProductName name;
    private double price;
    private int amount;

}
