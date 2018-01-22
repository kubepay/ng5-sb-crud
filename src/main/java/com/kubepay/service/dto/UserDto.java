package com.kubepay.service.dto;

import com.kubepay.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private final Long userId;
    private final String userFirstName;
    private final String userLastName;
    private final String userEmail;

    public UserDto(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    public User toUser() {
        final User user = new User(userFirstName, userLastName, userEmail);
        user.setId(userId);
        return user;
    }

}
