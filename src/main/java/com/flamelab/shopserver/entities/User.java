package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.internal_data.Product;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends CommonEntity {

    private String name;
    private ObjectId walletId;
    private List<Product> basket;

}
