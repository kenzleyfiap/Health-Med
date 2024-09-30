package br.com.health.service;

import br.com.health.domain.paciente.Paciente;
import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;
import br.com.health.mapper.PacienteMapper;
import br.com.health.repository.PacienteRepository;
import br.com.health.service.impl.PacienteServiceImpl;
import br.com.health.utils.PacienteHelper;
import br.com.health.validators.paciente.ValidadorCadastroPaciente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PacienteServiceTest {

    private PacienteService pacienteService;
    @Mock
    private PacienteRepository pacienteRepository;
    @Mock
    private PacienteMapper mapper;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private List<ValidadorCadastroPaciente> validadorCadastroPaciente;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        pacienteService = new PacienteServiceImpl(pacienteRepository,mapper, usuarioService, validadorCadastroPaciente);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void devePermitirBuscarTodosPacientesAtivos() {
        List<Paciente> pacientes = List.of(PacienteHelper.gerarPaciente());
        Page<Paciente> pagedResponse = new PageImpl<>(pacientes);

        when(pacienteRepository.findAllByAtivoTrue(any(Pageable.class)))
                .thenReturn(pagedResponse);

        Page<PacienteResponseDTO> result = pacienteService.findAllByAtivoTrue(PageRequest.of(0, 10));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

    }

    @Test
    void existsById() {
        when(pacienteRepository.existsById(any(Long.class))).thenReturn(Boolean.TRUE);
        boolean existsById = pacienteService.existsById(1L);

        Assertions.assertTrue(existsById);
    }

    @Test
    void getReferenceById() {

        when(pacienteRepository.findById(any(Long.class))).thenReturn(Optional.of(PacienteHelper.gerarPaciente()));
        when(mapper.entityToResponseDTO(any(Paciente.class))).thenReturn(PacienteHelper.gerarPacienteResponseDTO());

        PacienteResponseDTO paciente = pacienteService.getReferenceById(1L);

        assertThat(paciente).isInstanceOf(PacienteResponseDTO.class).isNotNull();
    }

    @Test
    void save() {
        when(mapper.dtoToEntity(any(PacienteDTO.class))).thenReturn(PacienteHelper.gerarPaciente());
        when(mapper.entityToResponseDTO(any(Paciente.class))).thenReturn(PacienteHelper.gerarPacienteResponseDTO());
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(PacienteHelper.gerarPaciente());

        PacienteResponseDTO save = pacienteService.save(PacienteHelper.gerarPacienteDTO());

        assertThat(save).isInstanceOf(PacienteResponseDTO.class).isNotNull();
    }

    @Test
    void getPaciente() {
        when(pacienteRepository.findById(any(Long.class))).thenReturn(Optional.of(PacienteHelper.gerarPaciente()));
        Paciente paciente = pacienteService.getPaciente(1L);
        assertThat(paciente).isInstanceOf(Paciente.class).isNotNull();
    }
}