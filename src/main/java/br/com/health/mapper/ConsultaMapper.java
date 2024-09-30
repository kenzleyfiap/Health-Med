package br.com.health.mapper;

import br.com.health.domain.consulta.Consulta;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.consulta.ConsultaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    Consulta dtoToEntity(ConsultaDTO consultaDTO);

    List<Consulta> dtoToEntity(Iterable<ConsultaDTO> consultaDTOS);

    @Mapping(source = "medico.id", target = "idMedico")
    @Mapping(source = "paciente.id", target = "idPaciente")
    ConsultaResponseDTO entityToResponseDTO(Consulta consulta);
}
