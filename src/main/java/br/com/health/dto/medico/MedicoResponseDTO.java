package br.com.health.dto.medico;

import br.com.health.domain.medico.Especialidade;

import java.util.List;

public record MedicoResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String crm,
        Especialidade especialidade,
        List<HorarioDisponivelDTO> horariosDisponiveis) {
}
