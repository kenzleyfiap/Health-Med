package br.com.health.service;

import br.com.health.domain.consulta.Consulta;
import br.com.health.domain.medico.Medico;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.consulta.ConsultaResponseDTO;
import br.com.health.mapper.ConsultaMapper;
import br.com.health.repository.ConsultaRepository;
import br.com.health.service.impl.ConsultaServiceImpl;
import br.com.health.utils.ConsultaHelper;
import br.com.health.utils.MedicoHelper;
import br.com.health.utils.PacienteHelper;
import br.com.health.validators.consulta.ValidadorAgendamentoDeConsulta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ConsultaServiceTest {

    private ConsultaService consultaService;
    @Mock
    private ConsultaRepository consultaRepository;
    @Mock
    private ConsultaMapper mapper;
    @Mock
    private List<ValidadorAgendamentoDeConsulta> validadores;
    @Mock
    private MedicoService medicoService;
    @Mock
    private PacienteService pacienteService;
    @Mock
    private EmailService emailService;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        consultaService = new ConsultaServiceImpl(
                consultaRepository,
                mapper,
                validadores,
                medicoService,
                pacienteService,
                emailService
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void existsByMedicoIdAndData() {
        when(consultaRepository.existsByMedicoIdAndData(anyLong(), any(LocalDateTime.class))).thenReturn(Boolean.TRUE);
        boolean b = consultaService.existsByMedicoIdAndData(1L, LocalDateTime.now());

        assertTrue(b);

        verify(consultaRepository, times(1)).existsByMedicoIdAndData(anyLong(), any(LocalDateTime.class));
    }

    @Test
    void existsByPacienteIdAndDataBetween() {

     when(consultaRepository.existsByPacienteIdAndDataBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(true);

        boolean b = consultaService.existsByPacienteIdAndDataBetween(1L, LocalDateTime.now(), LocalDateTime.now());

        assertTrue(b);
        verify(consultaRepository, times(1)).existsByPacienteIdAndDataBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));

    }

    @Test
    void save() {
        when(consultaRepository.existsByMedicoIdAndData(anyLong(), any(LocalDateTime.class))).thenReturn(Boolean.FALSE);
        when(consultaRepository.existsByPacienteIdAndDataBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Boolean.FALSE);

        when(mapper.dtoToEntity(any(ConsultaDTO.class))).thenReturn(ConsultaHelper.gerarConsulta());
        when(medicoService.verificarDisponibilidade(any(Medico.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(MedicoHelper.gerarHorarioDisponivel()));
        when(mapper.entityToResponseDTO(any(Consulta.class))).thenReturn(ConsultaHelper.gerarConsultaResponseDTO());

        when(medicoService.existsById(anyLong())).thenReturn(Boolean.TRUE);
        when(pacienteService.existsById(anyLong())).thenReturn(Boolean.TRUE);

        when(pacienteService.getPaciente(any(Long.class))).thenReturn(PacienteHelper.gerarPaciente());
        when(medicoService.getMedico(any(Long.class))).thenReturn(MedicoHelper.gerarMedico());

        when(consultaRepository.save(any(Consulta.class))).thenReturn(ConsultaHelper.gerarConsulta());

        ConsultaResponseDTO consulta = consultaService.save(ConsultaHelper.gerarConsultaDTO());

        assertThat(consulta.id()).isNotNull();
        verify(consultaRepository, times(1)).save(any(Consulta.class));

    }
}