package br.com.health.utils;

import br.com.health.domain.consulta.Consulta;
import br.com.health.domain.medico.Especialidade;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.consulta.ConsultaResponseDTO;

import java.time.LocalDateTime;

public abstract class ConsultaHelper {

    public static Consulta gerarConsulta() {
        Consulta consulta = new Consulta();
        consulta.setPaciente(PacienteHelper.gerarPaciente());
        consulta.setMedico(MedicoHelper.gerarMedico());
        consulta.setId(1L);
        consulta.setData(LocalDateTime.now());

        return consulta;
    }

    public static ConsultaDTO gerarConsultaDTO() {
        return new ConsultaDTO(1L, 1L, LocalDateTime.now(), Especialidade.ORTOPEDIA);
    }

    public static ConsultaResponseDTO gerarConsultaResponseDTO() {
        return new ConsultaResponseDTO(1L, 1L, 1L,LocalDateTime.now());
    }
}
