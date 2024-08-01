package com.novmah.restaurantmanagement.dto.response;

import com.novmah.restaurantmanagement.dto.OrderItemDto;
import com.novmah.restaurantmanagement.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {

    private String userEmail;
    private String userName;
    private Set<Role> userRole;
    private Integer tableNumber;
    private List<OrderItemDto> orderItemDtoList;
    private BigDecimal totalPrice;

}
