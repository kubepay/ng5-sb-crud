package com.kubepay.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.kubepay.entity.User;
import com.kubepay.repository.UserRepository;
import com.kubepay.service.dto.UserDto;
import com.kubepay.service.exception.UserNotFoundException;
import com.kubepay.service.exception.UserServiceException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserById() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        when(userRepository.findOne(1L)).thenReturn(user);

        UserDto dto = userService.getUserById(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.getUserFirstName()).isEqualTo("Daenerys");
        assertThat(dto.getUserLastName()).isEqualTo("Targaryen");
        assertThat(dto.getUserEmail()).isEqualTo("motherofdragons@got.com");

        verify(userRepository, times(1)).findOne(1L);
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByIdThrowsException() throws UserNotFoundException {
        when(userRepository.findOne(1L)).thenReturn(null);

        UserDto dto = userService.getUserById(1L);

        assertThat(dto).isNull();

        verify(userRepository, times(1)).findOne(1L);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(new User("Daenerys", "Targaryen", "motherofdragons@got.com"),
                new User("John", "Snow", "youknownothing@got.com"));
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> dtos = userService.getAllUsers();

        assertThat(dtos).isNotEmpty().hasSize(2).first().hasFieldOrPropertyWithValue("userFirstName", "Daenerys");

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testCreateUser() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");

        UserDto dto = new UserDto(user);
        UserDto expectedDto = new UserDto(1L, "Daenerys", "Targaryen", "motherofdragons@got.com");
        when(userRepository.save(user)).thenReturn(expectedDto.toUser());

        UserDto actualDto = userService.createUser(dto);

        assertThat(actualDto).isNotNull().isEqualTo(expectedDto);

        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected = UserServiceException.class)
    public void testCreateUserThrowsException() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        user.setId(1L);
        UserDto dto = new UserDto(user);
        UserDto expectedDto = new UserDto(1L, "Daenerys", "Targaryen", "motherofdragons@got.com");
        when(userRepository.save(user)).thenReturn(expectedDto.toUser());

        UserDto actualDto = userService.createUser(dto);

        assertThat(actualDto).isNotNull().isEqualTo(expectedDto);

        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testUpdateUser() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        user.setId(1L);

        UserDto dto = new UserDto(user);
        UserDto expectedDto = new UserDto(1L, "Daenerys", "Targaryen", "motherofdragons@got.com");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedDto.toUser());
        when(userRepository.findOne(1L)).thenReturn(user);

        UserDto actualDto = userService.updateUser(dto.getUserId(), dto);

        assertThat(actualDto).isNotNull().isEqualTo(expectedDto);

        verify(userRepository, times(1)).findOne(1L);
        verify(userRepository, times(1)).save(Mockito.any(User.class));
        verifyNoMoreInteractions(userRepository);
    }




    @Test(expected=UserNotFoundException.class)
    public void testUpdateUserNoFound() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        user.setId(1L);

        UserDto dto = new UserDto(user);
        UserDto expectedDto = new UserDto(1L, "Daenerys", "Targaryen", "motherofdragons@got.com");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedDto.toUser());
        when(userRepository.findOne(1L)).thenReturn(null);

        UserDto actualDto = userService.updateUser(dto.getUserId(), dto);

        assertThat(actualDto).isNotNull().isEqualTo(expectedDto);

        verify(userRepository, times(1)).findOne(1L);
        verify(userRepository, times(1)).save(Mockito.any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected=UserServiceException.class)
    public void testUpdateUserWithoutId() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        //user.setId(1L);

        UserDto dto = new UserDto(user);
        UserDto expectedDto = new UserDto(1L, "Daenerys", "Targaryen", "motherofdragons@got.com");

        when(userRepository.save(user)).thenReturn(expectedDto.toUser());
        when(userRepository.findOne(1L)).thenReturn(null);

        UserDto actualDto = userService.updateUser(dto.getUserId(), dto);

        assertThat(actualDto).isNotNull().isEqualTo(expectedDto);

        verify(userRepository, times(1)).findOne(1L);
        verify(userRepository, times(1)).save(Mockito.any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected=UserServiceException.class)
    public void testUpdateUserDifferentUser() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        user.setId(1L);

        UserDto dto = new UserDto(user);
        UserDto expectedDto = new UserDto(2L, "Daenerys", "Targaryen", "motherofdragons@got.com");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(expectedDto.toUser());
        when(userRepository.findOne(1L)).thenReturn(expectedDto.toUser());

        UserDto actualDto = userService.updateUser(dto.getUserId(), dto);

        assertThat(actualDto).isNotNull().isEqualTo(expectedDto);

        verify(userRepository, times(1)).findOne(1L);
        verify(userRepository, times(1)).save(Mockito.any(User.class));
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    public void testDeleteUser() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        user.setId(1L);
        when(userRepository.findOne(1L)).thenReturn(user);
        UserDto expectedDto = new UserDto(user);
        doNothing().when(userRepository).delete(1L);
        ;

        UserDto dto = userService.deleteUser(1L);

        assertThat(expectedDto).isNotNull().isEqualTo(dto);

        verify(userRepository, times(1)).findOne(1L);
        verify(userRepository, times(1)).delete(Mockito.any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected = UserNotFoundException.class)
    public void testDeleteUserThrowsException() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        user.setId(1L);
        when(userRepository.findOne(1L)).thenReturn(null);
        UserDto expectedDto = new UserDto(user);
        doNothing().when(userRepository).delete(1L);
        ;

        UserDto dto = userService.deleteUser(1L);

        assertThat(expectedDto).isNotNull().isEqualTo(dto);

        verify(userRepository, times(1)).findOne(1L);
        verify(userRepository, times(1)).delete(Mockito.any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testExistsByEmail() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        user.setId(1L);
        when(userRepository.findByEmail("motherofdragons@got.com")).thenReturn(user);

        UserDto expectedDto = new UserDto(user);

        boolean result = userService.existsByEmail(expectedDto);

        assertThat(result).isTrue();

        verify(userRepository, times(1)).findByEmail("motherofdragons@got.com");
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testExistsByNoEmail() {
        User user = new User("Daenerys", "Targaryen", null);
        user.setId(1L);

        UserDto expectedDto = new UserDto(user);

        boolean result = userService.existsByEmail(expectedDto);

        assertThat(result).isFalse();

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testExistsByEmailNotFound() {
        User user = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        user.setId(1L);
        when(userRepository.findByEmail("motherofdragons@got.com")).thenReturn(null);

        UserDto expectedDto = new UserDto(user);

        boolean result = userService.existsByEmail(expectedDto);

        assertThat(result).isFalse();

        verify(userRepository, times(1)).findByEmail("motherofdragons@got.com");
        verifyNoMoreInteractions(userRepository);
    }

}
