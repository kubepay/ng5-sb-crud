package com.kubepay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kubepay.service.UserService;
import com.kubepay.service.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:4200")
@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("getting all users");
        final List<UserDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            log.info("no users found");
            return new ResponseEntity<List<UserDto>>(HttpStatus.NO_CONTENT);
        }
        users.stream().forEach(user -> log.debug(user.toString()));
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") final Long id) {
        log.info("getting user with id: {}", id);
        final UserDto user = userService.getUserById(id);
        if (user == null) {
            log.info("user with id {} not found", id);
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> createUser(@RequestBody final UserDto user) {
        log.info("creating new user: {}", user);
        if (userService.existsByEmail(user)) {
            log.info("a user with email " + user.getUserEmail() + " already exists");
            return new ResponseEntity<UserDto>(user, HttpStatus.CONFLICT);
        }
        final UserDto userDtoUpdated = userService.createUser(user);
        return new ResponseEntity<UserDto>(userDtoUpdated, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateArticle(@PathVariable("id") final Long id, @RequestBody final UserDto user) {
        log.info("updating user: {}", user);
        final UserDto userDto = userService.updateUser(id, user);
        if (userDto == null) {
            log.info("user with id {} not found", id);
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserDto> deleteArticle(@PathVariable("id") final Long id) {
        log.info("deleting user with id: {}", id);
        final UserDto userDtoDeleted = userService.deleteUser(id);
        if (userDtoDeleted == null) {
            log.info("user with id {} not found", id);
            return new ResponseEntity<UserDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDto>(userDtoDeleted, HttpStatus.OK);
    }

}
