package com.flamelab.shopserver.dtos.update;

import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.internal_data.Product;
import com.mongodb.lang.Nullable;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UpdateUserDto extends CommonUpdateDto {

    private String name;
    private String email;
    private Roles role;
    @Nullable
    private ObjectId walletId;
    private List<Product> basket = new ArrayList<>();

}
