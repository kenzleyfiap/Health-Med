package br.com.health.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class EmailServiceIT {

    @Autowired
    private EmailService emailService;

    @Test
    void enviarEmailConsultaAgendada() {

        String s = emailService.enviarEmailConsultaAgendada("teste@mail.com", "teste", "teste", LocalDateTime.now());
        assertTrue(s.equals("Email enviado"));


    }
}