package com.flamelab.shopserver.entities;

import com.flamelab.shopserver.utiles.data.ObjectWithData;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonEntity extends ObjectWithData {

    private ObjectId id;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

}
