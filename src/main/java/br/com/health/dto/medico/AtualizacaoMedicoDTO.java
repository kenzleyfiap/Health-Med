package br.com.health.dto.medico;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AtualizacaoMedicoDTO(
        @NotNull
        Long id,
        List<HorarioDisponivelDTO> horariosDisponiveis
) {
}
