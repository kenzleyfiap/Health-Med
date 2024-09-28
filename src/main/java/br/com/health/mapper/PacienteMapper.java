package br.com.health.mapper;

import br.com.health.domain.Paciente;
import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    Paciente dtoToEntity(PacienteDTO pacienteDTO);

    @Mapping(target = "password", ignore = true)
    List<Paciente> dtoToEntity(Iterable<PacienteDTO> pacienteDTOS);

    PacienteDTO entityToDTO(Paciente paciente);

    List<PacienteDTO> entityToDTO(Iterable<Paciente> pacientes);

    PacienteResponseDTO entityToResponseDTO(Paciente paciente);

    List<PacienteResponseDTO> entityToResponseDTO(Iterable<Paciente> pacientes);
}
