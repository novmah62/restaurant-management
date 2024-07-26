package com.novmah.restaurantmanagement.dto;

import com.novmah.restaurantmanagement.entity.status.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    private String name;

    private String email;

    private String phoneNumber;

    private UserStatus status;


}
