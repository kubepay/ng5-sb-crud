package com.kubepay.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.kubepay.service.dto.UserDto;

public class UserDtoTest {

    @Test
    public void test() {
        UserDto userA = new UserDto(1L, "Daenerys", "Targaryen", "motherofdragons@got.com");
        UserDto userB = new UserDto(1L, "Daenerys", "Targaryen", "motherofdragons@got.com");
        UserDto userC = new UserDto(2L, "Arya", "Stark", "noone@got.com");
        UserDto userD = new UserDto(1L, "Arya", "Targaryen", "motherofdragons@got.com");
        UserDto userE = new UserDto(1L, "Daenerys", "Stark", "motherofdragons@got.com");
        UserDto userF = new UserDto(1L, "Daenerys", "Targaryen", "noone@got.com");
        User userG = new User();
        assertThat(userA.hashCode()).isNotNull();
        assertThat(userA).isEqualTo(userB);
        assertThat(userA).isNotEqualTo(userC);
        assertThat(userA).isNotEqualTo(userD);
        assertThat(userA).isNotEqualTo(userE);
        assertThat(userA).isNotEqualTo(userF);
        assertThat(userA).isNotEqualTo(userG);
        assertThat(userA).isNotEqualTo(null);
        assertThat(userB.toString()).isNotEmpty();
    }

}
