package br.com.health.dto.medico;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HorarioDisponivelDTO(
        Long id,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        DayOfWeek dia,
        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaInicio,
        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaFim
) {
}
