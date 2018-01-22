package com.kubepay.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kubepay.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() throws InterruptedException {

        final List<User> beforeUsers = userRepository.findAll();

        final User userToSave = new User("John", "Doe", "john@email.com");

        // Function [Save] Test
        final User userSaved = userRepository.save(userToSave);
        System.out.println(userSaved);
        assertThat(userSaved.getCreated()).isNotNull();
        assertThat(userSaved.getModified()).isNotNull();
        assertThat(userSaved.getCreatedBy()).isEqualTo("Mr. Auditor");
        assertThat(userSaved.getModifiedBy()).isEqualTo("Mr. Auditor");
        assertThat(userSaved.getFirstName()).isNotNull().isEqualTo("John");
        assertThat(userSaved.getLastName()).isNotNull().isEqualTo("Doe");
        assertThat(userSaved.getEmail()).isNotNull().isEqualTo("john@email.com");

        final Long id = userSaved.getId();
        userSaved.setFirstName("Abhishek");
        userSaved.setLastName("Pandey");
        userSaved.setEmail("abhiesa@outlook.com");


        // Function [Update] Test
        final ZonedDateTime created = userSaved.getCreated();
        final ZonedDateTime modified = userSaved.getModified();
        final User updatedUser = userRepository.save(userSaved);
        System.out.println(updatedUser);


        assertThat(updatedUser.getId()).isNotNull().isEqualTo(id);
        assertThat(updatedUser.getFirstName()).isNotNull().isEqualTo("Abhishek");
        assertThat(updatedUser.getLastName()).isNotNull().isEqualTo("Pandey");
        assertThat(updatedUser.getEmail()).isNotNull().isEqualTo("abhiesa@outlook.com");

        assertThat(updatedUser.getCreated()).isEqualTo(created);
        assertThat(updatedUser.getModified()).isGreaterThan(modified);

        final User searchedUser = userRepository.findByEmail(updatedUser.getEmail());
        assertThat(searchedUser).isNotNull().isEqualTo(updatedUser);

        // Function [Delete] Test
        userRepository.delete(searchedUser.getId());

        // Function [FindOne] Test
        final User deletedUser = userRepository.findOne(searchedUser.getId());
        assertThat(deletedUser).isNull();

        // Function [FindAll] Test
        final List<User> afterUsers = userRepository.findAll();
        assertThat(afterUsers.size()).isEqualTo(beforeUsers.size());

    }

}
