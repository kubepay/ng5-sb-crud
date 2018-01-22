package com.kubepay.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kubepay.entity.User;
import com.kubepay.repository.UserRepository;
import com.kubepay.service.UserService;
import com.kubepay.service.dto.UserDto;
import com.kubepay.service.exception.UserNotFoundException;
import com.kubepay.service.exception.UserServiceException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUserById(final Long id) {
        final User user = userRepository.findOne(id);
        if (null == user)
            throw new UserNotFoundException(id);
        return new UserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(@NotNull final UserDto userDto) {
        if (null != userDto.getUserId())
            throw new UserServiceException("User Creation request can't hold UserId property", true);
        final User user = userRepository.save(userDto.toUser());
        return new UserDto(user);
    }

    @Override
    public UserDto updateUser(final Long id, @NotNull final UserDto userDto) {
        if (null == userDto.getUserId())
            throw new UserServiceException("User Creation request can't hold UserId property", true);
        final User user = userRepository.findOne(id);
        if (null == user)
            throw new UserNotFoundException(id);
        if (user.getId().longValue() != userDto.getUserId().longValue())
            throw new UserServiceException("User requwst body didn't match with requesting path id", true);

        user.setFirstName(userDto.getUserFirstName());
        user.setLastName(userDto.getUserFirstName());
        user.setEmail(userDto.getUserEmail());

        final User updatedUser = userRepository.save(userDto.toUser());
        return new UserDto(updatedUser);
    }

    @Override
    public UserDto deleteUser(final Long id) {
        final User user = userRepository.findOne(id);
        if (null == user)
            throw new UserNotFoundException(id);
        userRepository.delete(user);
        return new UserDto(user);
    }

    @Override
    public boolean existsByEmail(UserDto user) {
        if (null != user.getUserEmail())
            return null != userRepository.findByEmail(user.getUserEmail());
        else
            return false;
    }

}
