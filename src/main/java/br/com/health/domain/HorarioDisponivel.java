package br.com.health.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Table(name = "horario_disponivel")
@Entity(name = "HorarioDisponivel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class HorarioDisponivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dia;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    public HorarioDisponivel(DayOfWeek dia, LocalTime horaInicio, LocalTime horaFim, Medico medico) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.medico = medico;
    }
}
