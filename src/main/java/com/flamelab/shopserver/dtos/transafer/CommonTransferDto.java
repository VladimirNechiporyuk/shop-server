package com.flamelab.shopserver.dtos.transafer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.flamelab.shopserver.utiles.data.ObjectWithData;
import com.flamelab.shopserver.utiles.serializers.StringJsonSerializer;
import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class CommonTransferDto extends ObjectWithData {

    @JsonSerialize(using = StringJsonSerializer.class)
    private ObjectId id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

}
