package br.com.health.mapper;

import br.com.health.domain.Consulta;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.consulta.ConsultaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

    Consulta dtoToEntity(ConsultaDTO consultaDTO);

    List<Consulta> dtoToEntity(Iterable<ConsultaDTO> consultaDTOS);

    ConsultaDTO entityToDTO(Consulta consulta);

    List<ConsultaDTO> entityToDTO(Iterable<Consulta> consultas);

    ConsultaResponseDTO entityToResponseDTO(Consulta consulta);

    List<ConsultaResponseDTO> entityToResponseDTO(Iterable<Consulta> consultas);
}
