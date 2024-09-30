package br.com.health.controller;

import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;
import br.com.health.service.PacienteService;
import br.com.health.utils.PacienteHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PacienteControllerTest {

    private MockMvc mockMvc;
    @Mock
    private PacienteService pacienteService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        PacienteController pacienteController = new PacienteController(pacienteService);
        mockMvc = MockMvcBuilders.standaloneSetup(pacienteController)
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
    void cadastrar() throws Exception {

        var paciente = PacienteHelper.gerarPacienteDTO();

        when(pacienteService.save(any(PacienteDTO.class)))
                .thenReturn(PacienteHelper.gerarPacienteResponseDTO());

        // Act && Assert
        mockMvc.perform(
                post("/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paciente))
        ).andExpect(status().isOk());

        verify(pacienteService, times(1)).save(any(PacienteDTO.class));


    }

    @Test
    void listar() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);  // Página 0 com 10 itens

        PacienteResponseDTO pacienteResponseDTO = PacienteHelper.gerarPacienteResponseDTO();
        var page = new PageImpl<>(Collections.singletonList(pacienteResponseDTO), pageable, 1);

        when(pacienteService.findAllByAtivoTrue(any(Pageable.class))).thenReturn(page);

        // Performar a requisição de listar pacientes
        mockMvc.perform(get("/pacientes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", not(empty())))
                .andExpect(jsonPath("$.content[0].id").value(pacienteResponseDTO.id()))
                .andExpect(jsonPath("$.content[0].nome").value(pacienteResponseDTO.nome()));

        // Verificar que o método foi chamado
        verify(pacienteService, times(1)).findAllByAtivoTrue(any(Pageable.class));
    }

    @Test
    void detalhar() throws Exception {
        // Simular o comportamento do service
        PacienteResponseDTO pacienteResponseDTO = PacienteHelper.gerarPacienteResponseDTO();

        when(pacienteService.getReferenceById(1L)).thenReturn(pacienteResponseDTO);

        // Act && Assert
        mockMvc.perform(get("/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("paciente"));

        verify(pacienteService, times(1)).getReferenceById(1L);
    }

    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        return objectMapper.writeValueAsString(object);
    }
}