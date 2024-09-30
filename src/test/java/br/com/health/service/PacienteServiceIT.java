package br.com.health.service;

import br.com.health.domain.paciente.Paciente;
import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;
import br.com.health.mapper.PacienteMapper;
import br.com.health.repository.PacienteRepository;
import br.com.health.service.impl.PacienteServiceImpl;
import br.com.health.utils.PacienteHelper;
import br.com.health.validators.paciente.ValidadorCadastroPaciente;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class PacienteServiceIT {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private PacienteMapper mapper;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private List<ValidadorCadastroPaciente> validadorCadastroPaciente;


    @Test
    void devePermitirBuscarTodosPacientesAtivos() {

        Page<PacienteResponseDTO> allByAtivoTrue = pacienteService.findAllByAtivoTrue(PageRequest.of(0, 10));
        assertNotNull(allByAtivoTrue.getContent());

    }

    @Test
    void deveExistirPeloId() {
        boolean existsById = pacienteService.existsById(1L);
        assertTrue(existsById);
    }

    @Test
    void deveBuscarPeloId() {

        PacienteResponseDTO referenceById = pacienteService.getReferenceById(1L);

        assertThat(referenceById)
                .isNotNull()
                .isInstanceOf(PacienteResponseDTO.class);

        assertThat(referenceById.id()).isNotNull();
    }

    @Test
    void deveSalvar() {

        PacienteResponseDTO paciente = pacienteService.save(PacienteHelper.gerarPacienteDTO());

        assertThat(paciente)
                .isNotNull()
                .isInstanceOf(PacienteResponseDTO.class);

        assertThat(paciente.id()).isNotNull();

    }

    @Test
    void getPaciente() {
        Paciente paciente = pacienteService.getPaciente(1L);

        assertThat(paciente)
                .isNotNull()
                .isInstanceOf(Paciente.class);

        assertThat(paciente.getId()).isNotNull();

    }
}