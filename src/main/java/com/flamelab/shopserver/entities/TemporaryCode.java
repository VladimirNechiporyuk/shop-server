package com.flamelab.shopserver.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "temp_codes")
public class TemporaryCode extends CommonEntity {

    private String email;
    private int tempCode;

}
