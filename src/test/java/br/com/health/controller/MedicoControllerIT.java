package br.com.health.controller;

import br.com.health.config.TestSecurityConfig;
import br.com.health.domain.medico.Especialidade;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.utils.MedicoHelper;
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
import static io.restassured.RestAssured.when;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestSecurityConfig.class)
class MedicoControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


    @Test
    void cadastrar() throws Exception {

        var medico = MedicoHelper.gerarMedicoDTO();

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new MedicoDTO(null, "teste","02917413476", "testemedico@mail.com", "11223", "teste", Especialidade.ORTOPEDIA, medico.horariosDisponiveis()))
                .when()
                .post("/medicos")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void listar() throws Exception {

        when()
                .get("/medicos")
                .then()
                .statusCode(HttpStatus.OK.value());


    }
}
