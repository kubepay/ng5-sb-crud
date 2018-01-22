package com.kubepay.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubepay.conf.CORSFilter;
import com.kubepay.service.dto.UserDto;
import com.kubepay.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerIT {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).addFilters(new CORSFilter()).build();
    }

    // =========================================== Get All Users
    // ==========================================

    @Test
    public void test_get_all_success() throws Exception {
        List<UserDto> users = Arrays.asList(new UserDto(1L, "Daenerys", "Targaryen", "motherofdragons@got.com"),
                new UserDto(2L, "John", "Snow", "youknownothing@got.com"));

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].userFirstName", is("Daenerys")))
                .andExpect(jsonPath("$[1].userId", is(2))).andExpect(jsonPath("$[1].userLastName", is("Snow")));

        verify(userService, times(1)).getAllUsers();
        verifyNoMoreInteractions(userService);
    }

    // =========================================== Get User By ID
    // =========================================

    @Test
    public void test_get_by_id_success() throws Exception {
        UserDto user = new UserDto(1L, "Daenerys", "Targaryen", "motherofdragons@got.com");

        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", 1)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.userId", is(1))).andExpect(jsonPath("$.userFirstName", is("Daenerys")));

        verify(userService, times(1)).getUserById(1L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {

        when(userService.getUserById(1L)).thenReturn(null);

        mockMvc.perform(get("/users/{id}", 1)).andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(1L);
        verifyNoMoreInteractions(userService);
    }

    // =========================================== Create New User
    // ========================================

    @Test
    public void test_create_user_success() throws Exception {
        UserDto user = new UserDto(null, "Arya", "Stark", "noone@got.com");
        UserDto newUser = new UserDto(1L, "Arya", "Stark", "noone@got.com");

        when(userService.existsByEmail(user)).thenReturn(false);
        when(userService.createUser(user)).thenReturn(newUser);


        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).existsByEmail(user);
        verify(userService, times(1)).createUser(user);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_create_user_fail_409_conflict() throws Exception {
        UserDto user = new UserDto(null, "Arya", "Stark", "noone@got.com");

        when(userService.existsByEmail(user)).thenReturn(true);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isConflict());

        verify(userService, times(1)).existsByEmail(user);
        verifyNoMoreInteractions(userService);
    }

    // =========================================== Update Existing User
    // ===================================

    @Test
    public void test_update_user_success() throws Exception {
        UserDto user = new UserDto(1L, "Arya", "Stark", "noone@got.com");

        when(userService.updateUser(user.getUserId(), user)).thenReturn(user);

        mockMvc.perform(put("/users/{id}", user.getUserId()).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))).andExpect(status().isOk());

        verify(userService, times(1)).updateUser(user.getUserId(), user);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_update_user_fail_404_not_found() throws Exception {
        UserDto user = new UserDto(1L, "Arya", "Stark", "noone@got.com");

        when(userService.updateUser(user.getUserId(), user)).thenReturn(null);

        mockMvc.perform(put("/users/{id}", user.getUserId()).contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user))).andExpect(status().isNotFound());

        verify(userService, times(1)).updateUser(user.getUserId(), user);
        verifyNoMoreInteractions(userService);
    }

    // =========================================== Delete User
    // ============================================

    @Test
    public void test_delete_user_success() throws Exception {
        UserDto user = new UserDto(1L, "Arya", "Stark", "noone@got.com");

        when(userService.deleteUser(user.getUserId())).thenReturn(user);

        mockMvc.perform(delete("/users/{id}", user.getUserId())).andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(user.getUserId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void test_delete_user_fail_404_not_found() throws Exception {
        UserDto user = new UserDto(1L, "Arya", "Stark", "noone@got.com");

        when(userService.deleteUser(user.getUserId())).thenReturn(null);

        mockMvc.perform(delete("/users/{id}", user.getUserId())).andExpect(status().isNotFound());

        verify(userService, times(1)).deleteUser(user.getUserId());
        verifyNoMoreInteractions(userService);
    }

    // =========================================== CORS Headers
    // ===========================================

    @Test
    public void test_cors_headers() throws Exception {
        mockMvc.perform(get("/users")).andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().string("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"))
                .andExpect(header().string("Access-Control-Allow-Headers", "*"))
                .andExpect(header().string("Access-Control-Max-Age", "3600"));
    }

    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
