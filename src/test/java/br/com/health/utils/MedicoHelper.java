package br.com.health.utils;

import br.com.health.domain.medico.Especialidade;
import br.com.health.domain.medico.HorarioDisponivel;
import br.com.health.domain.medico.Medico;
import br.com.health.dto.medico.AtualizacaoMedicoDTO;
import br.com.health.dto.medico.HorarioDisponivelDTO;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.dto.medico.MedicoResponseDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class MedicoHelper {

    public static Medico gerarMedico() {
        Medico medico = new Medico();
        List<HorarioDisponivel> horarioDisponivels = new ArrayList<>();
        horarioDisponivels.add(gerarHorarioDisponivel());
        medico.setAtivo(Boolean.TRUE);
        medico.setId(1L);
        medico.setCpf("31462526942");
        medico.setCrm("44444");
        medico.setEspecialidade(Especialidade.ORTOPEDIA);
        medico.setNome("Alessandra Alana Mariana Rocha");
        medico.setEmail("alessandra@mail.com");
        medico.setHorariosDisponiveis(horarioDisponivels);

        return medico;
    }

    public static HorarioDisponivel gerarHorarioDisponivel() {
        LocalTime agora = LocalTime.now();
        HorarioDisponivel horarioDisponivel = new HorarioDisponivel();
        horarioDisponivel.setId(1L);
        horarioDisponivel.setHoraFim(agora.plusHours(1));
        horarioDisponivel.setHoraInicio(agora);
        return horarioDisponivel;
    }

    public static MedicoDTO gerarMedicoDTO() {
        List<HorarioDisponivelDTO> horarioDisponivels = new ArrayList<>();
        horarioDisponivels.add(gerarHorarioDisponivelDTO());
        return new MedicoDTO(null,
                "Alessandra Alana Mariana Rocha",
                "31462526942",
                "alessandra@mail.com",
                "44444",
                "password",
                Especialidade.ORTOPEDIA,
                horarioDisponivels);
    }

    public static HorarioDisponivelDTO gerarHorarioDisponivelDTO() {
        LocalTime agora = LocalTime.now();
        return new HorarioDisponivelDTO(null, DayOfWeek.MONDAY, agora, agora.plusHours(1));
    }

    public static MedicoResponseDTO gerarMedicoResponseDTO() {
        List<HorarioDisponivelDTO> horarioDisponivels = List.of(gerarHorarioDisponivelDTO());
        return new MedicoResponseDTO(1L,
                "Alessandra Alana Mariana Rocha",
                "31462526942",
                "alessandra@mail.com",
                "44444",
                Especialidade.ORTOPEDIA,
                horarioDisponivels);
    }

    public static AtualizacaoMedicoDTO gerarAtualizacaoMedicoDTO() {
        LocalTime agora = LocalTime.now();
        var horario = new HorarioDisponivelDTO(null, DayOfWeek.MONDAY, agora.plusHours(2), agora.plusHours(5));
        return new AtualizacaoMedicoDTO(1L, List.of(horario));
    }
}
