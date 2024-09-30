package br.com.health.service;

import br.com.health.domain.medico.Especialidade;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.consulta.ConsultaResponseDTO;
import br.com.health.mapper.ConsultaMapper;
import br.com.health.repository.ConsultaRepository;
import br.com.health.validators.consulta.ValidadorAgendamentoDeConsulta;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class ConsultaServiceIT {

    @Autowired
    private ConsultaService consultaService;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private ConsultaMapper mapper;
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private EmailService emailService;

    @Test
    void existsByMedicoIdAndData() {
        boolean b = consultaService.existsByMedicoIdAndData(1L, LocalDateTime.now());
        assertFalse(b);
    }

    @Test
    void existsByPacienteIdAndDataBetween() {
        boolean b = consultaService.existsByPacienteIdAndDataBetween(1L, LocalDateTime.now(), LocalDateTime.now());
        assertFalse(b);

    }

    @Test
    void save() {

        LocalDate data = LocalDate.of(2024, 9, 30);
        LocalTime horario = LocalTime.of(8, 0);
        LocalDateTime dataConsulta = LocalDateTime.of(data, horario);

        ConsultaResponseDTO consulta = consultaService.save(new ConsultaDTO(1L, 1L, dataConsulta, Especialidade.ORTOPEDIA));

        assertThat(consulta.id()).isNotNull();

        assertThat(consulta)
                .isNotNull()
                .isInstanceOf(ConsultaResponseDTO.class);

    }
}