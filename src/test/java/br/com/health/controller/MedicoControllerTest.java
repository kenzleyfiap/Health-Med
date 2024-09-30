package br.com.health.controller;

import br.com.health.dto.medico.MedicoDTO;
import br.com.health.dto.medico.MedicoResponseDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;
import br.com.health.service.MedicoService;
import br.com.health.utils.MedicoHelper;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MedicoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MedicoService medicoService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        MedicoController mensagemController = new MedicoController(medicoService);
        mockMvc = MockMvcBuilders.standaloneSetup(mensagemController)
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
        // Arrange
        var medico = MedicoHelper.gerarMedicoDTO();
        when(medicoService.save(any(MedicoDTO.class)))
                .thenReturn(MedicoHelper.gerarMedicoResponseDTO());

        // Act && Assert
        mockMvc.perform(
                post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(medico))
        ).andExpect(status().isOk());

        verify(medicoService, times(1)).save(any(MedicoDTO.class));
    }

    @Test
    void listar() throws Exception {

        Pageable pageable = PageRequest.of(0, 10);  // Página 0 com 10 itens
        MedicoResponseDTO medicoResponseDTO = MedicoHelper.gerarMedicoResponseDTO();
        var page = new PageImpl<>(Collections.singletonList(medicoResponseDTO), pageable, 1);

        when(medicoService.findAllByAtivoTrue(any(Pageable.class))).thenReturn(page);

        // Performar a requisição de listar pacientes
        mockMvc.perform(get("/medicos")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", not(empty())))
                .andExpect(jsonPath("$.content[0].id").value(medicoResponseDTO.id()))
                .andExpect(jsonPath("$.content[0].nome").value(medicoResponseDTO.nome()));

        // Verificar que o método foi chamado
        verify(medicoService, times(1)).findAllByAtivoTrue(any(Pageable.class));


    }
    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        return objectMapper.writeValueAsString(object);
    }
}