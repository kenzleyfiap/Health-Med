package br.com.health.controller;

import br.com.health.domain.medico.Especialidade;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.consulta.ConsultaResponseDTO;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.service.ConsultaService;
import br.com.health.utils.ConsultaHelper;
import br.com.health.utils.MedicoHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsultaControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ConsultaService consultaService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        ConsultaController consultaController = new ConsultaController(consultaService);
        mockMvc = MockMvcBuilders.standaloneSetup(consultaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .addFilter((request,response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void agendar() throws Exception {


        LocalDate data = LocalDate.of(2024, 10, 7);
        LocalTime horario = LocalTime.of(8, 0);
        LocalDateTime dataConsulta = LocalDateTime.of(data, horario);


        when(consultaService.save(any(ConsultaDTO.class)))
                .thenReturn(ConsultaHelper.gerarConsultaResponseDTO());

        // Act && Assert
        mockMvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new ConsultaDTO(1L, 1L, dataConsulta, Especialidade.ORTOPEDIA)))
        ).andExpect(status().isOk());

        verify(consultaService, times(1)).save(any(ConsultaDTO.class));
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        return objectMapper.writeValueAsString(object);
    }
}