package br.com.health.controller;

import br.com.health.config.TestSecurityConfig;
import br.com.health.domain.medico.Especialidade;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.service.ConsultaService;
import br.com.health.utils.ConsultaHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestSecurityConfig.class)
class ConsultaControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void agendar() throws Exception {

        LocalDate data = LocalDate.of(2024, 10, 7);
        LocalTime horario = LocalTime.of(8, 0);
        LocalDateTime dataConsulta = LocalDateTime.of(data, horario);


        // Act && Assert
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ConsultaDTO(1L, 1L, dataConsulta, Especialidade.ORTOPEDIA))
                .when()
                .post("/consultas")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}