package br.com.health.controller;

import br.com.health.config.TestSecurityConfig;
import br.com.health.domain.medico.Especialidade;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.usuario.AutenticacaoDTO;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AutenticacaoControllerIT {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    @Test
    void login() {

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new AutenticacaoDTO("usuario4", "teste"))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}