package br.com.health.service;

import br.com.health.domain.medico.Especialidade;
import br.com.health.domain.medico.HorarioDisponivel;
import br.com.health.domain.medico.Medico;
import br.com.health.dto.medico.AtualizacaoMedicoDTO;
import br.com.health.dto.medico.HorarioDisponivelDTO;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.dto.medico.MedicoResponseDTO;
import br.com.health.mapper.HorarioDisponivelMapper;
import br.com.health.mapper.MedicoMapper;
import br.com.health.repository.MedicoRepository;
import br.com.health.service.impl.MedicoServiceImpl;
import br.com.health.utils.MedicoHelper;
import br.com.health.validators.medico.ValidadorCadastroMedico;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class MedicoServiceIT {
    @Autowired
    private MedicoService medicoService;
    @Autowired
    private MedicoRepository repository;
    @Autowired
    private MedicoMapper mapper;
    @Autowired
    private HorarioDisponivelMapper horarioDisponivelMapper;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private List<ValidadorCadastroMedico> validadorCadastroMedicos;



    @Test
    void findAllByAtivoTrue() {


        Page<MedicoResponseDTO> medicosPage = medicoService.findAllByAtivoTrue(PageRequest.of(0, 10));

        assertNotNull(medicosPage);

        assertThat(medicosPage)
                .isNotNull()
                .isInstanceOf(Page.class);

    }

    @Test
    void existsById() {

        boolean existsById = medicoService.existsById(1L);
        assertTrue(existsById);

    }

    @Test
    void findById() {

        MedicoResponseDTO medico = medicoService.findById(2L);

        assertThat(medico)
                .isNotNull()
                .isInstanceOf(MedicoResponseDTO.class);
    }

    @Test
    void save() {

        LocalTime agora = LocalTime.now();

        MedicoResponseDTO save = medicoService.save(new MedicoDTO(
                null,
                "teste",
                "70221127976",
                "teste@mail.com",
                "44411",
                "teste",
                Especialidade.ORTOPEDIA,
                List.of(MedicoHelper.gerarHorarioDisponivelDTO())
        ));

        assertThat(save)
                .isNotNull()
                .isInstanceOf(MedicoResponseDTO.class);
    }

    @Test
    void updateHorarioDisponiveis() {

        Optional<Medico> medico = repository.findById(1L);
        HorarioDisponivelDTO horarioDisponivelDTO = MedicoHelper.gerarHorarioDisponivelDTO();


        AtualizacaoMedicoDTO atualizacaoMedicoDTO = new AtualizacaoMedicoDTO(medico.get().getId(), List.of(horarioDisponivelDTO));

        MedicoResponseDTO medicoResponseDTO = medicoService.updateHorarioDisponiveis(atualizacaoMedicoDTO);

        assertThat(medicoResponseDTO)
                .isNotNull()
                .isInstanceOf(MedicoResponseDTO.class);


    }

    @Test
    void getMedico() {

        Medico medico = medicoService.getMedico(1L);

        assertThat(medico)
                .isNotNull()
                .isInstanceOf(Medico.class);


    }

    @Test
    void verificarDisponibilidade() {

        Medico medico = medicoService.getMedico(1L);

        Optional<HorarioDisponivel> horarioDisponivel = medicoService.verificarDisponibilidade(medico, LocalDateTime.now());

        assertNotNull(horarioDisponivel);

    }

    @Test
    void agendarConsulta() {
        Medico medico = medicoService.getMedico(1L);

        LocalDate data = LocalDate.of(2024, 9, 30);
        LocalTime horario = LocalTime.of(8, 0);
        LocalDateTime dataConsulta = LocalDateTime.of(data, horario);

        medicoService.agendarConsulta(medico, dataConsulta);

        assertNotNull(medico);

    }
}