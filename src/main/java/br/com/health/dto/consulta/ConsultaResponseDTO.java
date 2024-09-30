package br.com.health.dto.consulta;

import br.com.health.domain.consulta.Consulta;

import java.time.LocalDateTime;

public record ConsultaResponseDTO(Long id,
                                  Long idMedico,
                                  Long idPaciente,
                                  LocalDateTime data) {
    public ConsultaResponseDTO(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
