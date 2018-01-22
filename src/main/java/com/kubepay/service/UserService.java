package com.kubepay.service;

import java.util.List;

import com.kubepay.service.dto.UserDto;

public interface UserService {

    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    UserDto createUser(UserDto userDto);

    UserDto deleteUser(Long id);

    UserDto updateUser(Long id, UserDto user);

    boolean existsByEmail(UserDto user);

}
