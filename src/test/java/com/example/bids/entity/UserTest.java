package com.example.bids.entity;

import com.example.bids.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void userAddTest() {
        var user = User.builder().email("test@test.test")
                .userPw("1234")
                .userName("test")
                .addr1("address")
                .phoneNo("1234-1234")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        user = userRepository.save(user);
        System.out.println(user);
    }

    @Test
    void  findUserTest() {
        var optionalUser = userRepository.findById(1L);
        optionalUser.ifPresent(System.out::println);
    }
}