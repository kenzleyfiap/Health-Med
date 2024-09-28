package br.com.health.mapper;

import br.com.health.domain.HorarioDisponivel;
import br.com.health.dto.medico.HorarioDisponivelDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HorarioDisponivelMapper {

    List<HorarioDisponivel> dtoToEntity(Iterable<HorarioDisponivelDTO> horarioDisponivelDTOS);
}
