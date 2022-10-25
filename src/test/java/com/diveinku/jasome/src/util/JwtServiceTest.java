package com.diveinku.jasome.src.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JwtServiceTest {

    @Autowired
    JwtService jwtService;

    @Test
    void createJwt() {
        //Given
        long memberId = 4L;
        //When
        String jwt = jwtService.createJwt(memberId);
        //Then
        System.out.println("jwt = " + jwt);
    }
}