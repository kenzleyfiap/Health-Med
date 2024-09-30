package br.com.health.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class AuthorizationServiceIT {

    @Autowired
    AuthorizationService authorizationService;

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = authorizationService.loadUserByUsername("usuario1");

        assertThat(userDetails)
                .isNotNull()
                .isInstanceOf(UserDetails.class);
    }
}