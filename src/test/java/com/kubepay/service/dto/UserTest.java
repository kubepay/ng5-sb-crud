package com.kubepay.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.kubepay.entity.User;

public class UserTest {

    @Test
    public void test() {
        User userA = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        User userB = new User("Daenerys", "Targaryen", "motherofdragons@got.com");
        User userC = new User("Arya", "Stark", "noone@got.com");
        User userD = new User("Arya", "Targaryen", "motherofdragons@got.com");
        User userE = new User("Daenerys", "Stark", "motherofdragons@got.com");
        User userF = new User("Daenerys", "Targaryen", "noone@got.com");
        String userG = "Arya";
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
