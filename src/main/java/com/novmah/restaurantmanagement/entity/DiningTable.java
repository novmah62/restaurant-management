package com.novmah.restaurantmanagement.entity;

import com.novmah.restaurantmanagement.entity.status.TableStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dining_tables")
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer number;

    @Enumerated(EnumType.STRING)
    private TableStatus status;

}
