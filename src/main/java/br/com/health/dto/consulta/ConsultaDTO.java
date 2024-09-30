package br.com.health.dto.consulta;

import br.com.health.domain.medico.Especialidade;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaDTO(Long idMedico,
                          @NotNull
                          Long idPaciente,
                          @NotNull
                          @FutureOrPresent
                          LocalDateTime data,
                          Especialidade especialidade) {
}
