package com.novmah.restaurantmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.novmah.restaurantmanagement.entity.status.TableStatus;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableDto implements Serializable {

    @JsonIgnore
    private Integer id;

    private Integer number;

    private TableStatus status;

}
