package com.kubepay;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kubepay.entity.User;
import com.kubepay.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
@EnableTransactionManagement
public class HelloReleaseApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(final String[] args) {
        SpringApplication.run(HelloReleaseApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {

        userRepository
                .save(Arrays.asList(new User("John", "Doe", "john@email.com"),
                        new User("Jon", "Smith", "smith@email.com"), new User("Will", "Craig", "will@email.com"),
                        new User("Sam", "Lernorad", "sam@email.com"), new User("Ross", "Doe", "ross@email.com")))
                .stream().forEach(u -> log.info("Saving User: " + u));
    }

}
