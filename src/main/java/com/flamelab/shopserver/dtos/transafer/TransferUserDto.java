package com.flamelab.shopserver.dtos.transafer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.utiles.serializers.StringJsonSerializer;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TransferUserDto extends CommonTransferDto {

    private String name;
    private String email;
    @JsonSerialize(using = StringJsonSerializer.class)
    private ObjectId walletId;
    private List<Product> basket = new ArrayList<>();

}
