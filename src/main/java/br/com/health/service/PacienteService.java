package br.com.health.service;

import br.com.health.domain.Paciente;
import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PacienteService {

    boolean findAtivoById(Long idPaciente);
    Page<PacienteResponseDTO> findAllByAtivoTrue(Pageable paginacao);

    boolean existsById(Long idPaciente);

    PacienteResponseDTO getReferenceById(Long idPaciente);

    PacienteResponseDTO save(PacienteDTO pacienteDTO);

    public Paciente getPaciente(Long id);


}
