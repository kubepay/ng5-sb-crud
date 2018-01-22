package com.kubepay.service.impl;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.kubepay.repository.UserRepository;
import com.kubepay.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceIT {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl(userRepository);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserServiceImpl() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetUserById() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAllUsers() {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateUser() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateUser() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteUser() {
        fail("Not yet implemented");
    }

}
