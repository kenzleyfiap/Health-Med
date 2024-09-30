package br.com.health.controller;

import br.com.health.config.TestSecurityConfig;
import br.com.health.infra.security.SecurityConfigurations;
import br.com.health.utils.PacienteHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestSecurityConfig.class)
class PacienteControllerIT {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void cadastrar() {

        var paciente = PacienteHelper.gerarPacienteDTO();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(paciente)
                .when()
                .post("/pacientes")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void listar() {

        when()
                .get("/pacientes")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void detalhar(){

        when()
                .get("/pacientes/{id}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}