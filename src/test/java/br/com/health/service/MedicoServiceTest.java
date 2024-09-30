package br.com.health.service;

import br.com.health.domain.medico.HorarioDisponivel;
import br.com.health.domain.medico.Medico;
import br.com.health.dto.medico.AtualizacaoMedicoDTO;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.dto.medico.MedicoResponseDTO;
import br.com.health.mapper.HorarioDisponivelMapper;
import br.com.health.mapper.MedicoMapper;
import br.com.health.repository.MedicoRepository;
import br.com.health.service.impl.MedicoServiceImpl;
import br.com.health.utils.MedicoHelper;
import br.com.health.validators.medico.ValidadorCadastroMedico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class MedicoServiceTest {

    private MedicoService medicoService;
    @Mock
    private MedicoRepository repository;
    @Mock
    private MedicoMapper mapper;
    @Mock
    private HorarioDisponivelMapper horarioDisponivelMapper;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private List<ValidadorCadastroMedico> validadorCadastroMedicos;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        medicoService = new MedicoServiceImpl(repository, mapper,horarioDisponivelMapper, usuarioService, validadorCadastroMedicos);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void findAllByAtivoTrue() {
        List<Medico> medicos = List.of(MedicoHelper.gerarMedico());
        Page<Medico> medicoPage = new PageImpl<>(medicos);

        when(repository.findAllByAtivoTrue(any(Pageable.class)))
                .thenReturn(medicoPage);

        Page<MedicoResponseDTO> medicosPage = medicoService.findAllByAtivoTrue(PageRequest.of(0, 10));

        when(mapper.entityToResponseDTO(any(Medico.class))).thenReturn(MedicoHelper.gerarMedicoResponseDTO());

        verify(repository, times(1)).findAllByAtivoTrue(any(Pageable.class));

        assertNotNull(medicosPage);

    }

    @Test
    void existsById() {

        when(repository.existsById(1L)).thenReturn(Boolean.TRUE);
        boolean existsById = medicoService.existsById(1L);

        verify(repository, times(1)).existsById(any(Long.class));
        assertTrue(existsById);

    }

    @Test
    void findById() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(MedicoHelper.gerarMedico()));
        when(mapper.entityToResponseDTO(any(Medico.class))).thenReturn(MedicoHelper.gerarMedicoResponseDTO());
        MedicoResponseDTO medico = medicoService.findById(1L);

        assertNotNull(medico);
        verify(repository, times(1)).findById(any(Long.class));



    }

    @Test
    void save() {
        when(mapper.dtoToEntity(any(MedicoDTO.class))).thenReturn(MedicoHelper.gerarMedico());
        when(mapper.entityToResponseDTO(any(Medico.class))).thenReturn(MedicoHelper.gerarMedicoResponseDTO());
        when(repository.save(any(Medico.class))).thenReturn(MedicoHelper.gerarMedico());

        MedicoResponseDTO save = medicoService.save(MedicoHelper.gerarMedicoDTO());

        assertNotNull(save);
        verify(repository, times(1)).save(any(Medico.class));


    }

    @Test
    void updateHorarioDisponiveis() {

        Medico medico = MedicoHelper.gerarMedico();
        MedicoResponseDTO medicoResponseDTO1 = MedicoHelper.gerarMedicoResponseDTO();


        when(repository.findById(anyLong())).thenReturn(Optional.of(medico));
        when(mapper.entityToResponseDTO(any(Medico.class))).thenReturn(medicoResponseDTO1);
        when(horarioDisponivelMapper.dtoToEntity(anyList())).thenReturn(List.of(MedicoHelper.gerarHorarioDisponivel()));
        when(repository.save(any(Medico.class))).thenReturn(MedicoHelper.gerarMedico());

        AtualizacaoMedicoDTO atualizacaoMedicoDTO = MedicoHelper.gerarAtualizacaoMedicoDTO();
        MedicoResponseDTO medicoResponseDTO = medicoService.updateHorarioDisponiveis(atualizacaoMedicoDTO);

        assertNotNull(medicoResponseDTO);

        verify(repository, times(1)).save(any(Medico.class));
        verify(repository, times(1)).findById(any(Long.class));


    }

    @Test
    void getMedico() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(MedicoHelper.gerarMedico()));

        Medico medico = medicoService.getMedico(1L);

        assertNotNull(medico);
        verify(repository, times(1)).findById(any(Long.class));


    }

    @Test
    void verificarDisponibilidade() {

        Medico medico = MedicoHelper.gerarMedico();
        medico.getHorariosDisponiveis().get(0).setDia(DayOfWeek.MONDAY);

        Optional<HorarioDisponivel> horarioDisponivel = medicoService.verificarDisponibilidade(medico, LocalDateTime.now());

        assertNotNull(horarioDisponivel);

    }

    @Test
    void agendarConsulta() {
        Medico medico = MedicoHelper.gerarMedico();
        medico.getHorariosDisponiveis().get(0).setDia(DayOfWeek.MONDAY);

        LocalDateTime dataConsulta = LocalDateTime.of(2023, 9, 30, 10, 0);
        HorarioDisponivel horarioDisponivel = new HorarioDisponivel();
        horarioDisponivel.setDia(DayOfWeek.SATURDAY);
        horarioDisponivel.setHoraInicio(LocalTime.of(9, 0));
        horarioDisponivel.setHoraFim(LocalTime.of(12, 0));
        medico.getHorariosDisponiveis().add(horarioDisponivel);


        medicoService.agendarConsulta(medico, dataConsulta);

        verify(repository, times(1)).save(any(Medico.class));

    }
}