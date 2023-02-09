package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.internal_data.Product;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Shop extends CommonEntity {

    private String name;
    private ObjectId ownerId;
    private ObjectId walletId;
    private List<Product> products = new ArrayList<>();

}
