package br.com.health.mapper;

import br.com.health.domain.Medico;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.dto.medico.MedicoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "horariosDisponiveis", source = "horariosDisponiveis")
    Medico dtoToEntity(MedicoDTO medicoDTO);

    List<Medico> dtoToEntity(Iterable<MedicoDTO> medicoDTOS);

    MedicoDTO entityToDTO(Medico medico);

    List<MedicoDTO> entityToDTO(Iterable<Medico> medicos);

    MedicoResponseDTO entityToResponseDTO(Medico medico);

    List<MedicoResponseDTO> entityToResponseDTO(Iterable<Medico> medicos);
}
