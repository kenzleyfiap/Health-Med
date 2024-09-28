package br.com.health.domain;

import br.com.health.dto.medico.AtualizacaoMedicoDTO;
import br.com.health.dto.medico.MedicoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String crm;
    private String email;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    private Boolean ativo;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioDisponivel> horariosDisponiveis = new ArrayList<>();


    public Medico(MedicoDTO dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.cpf = dados.cpf();
        dados.horariosDisponiveis().forEach(horarios -> adicionarHorario(horarios.dia(),horarios.horaInicio(), horarios.horaFim()));
    }

    public void atualizarInformacoes(AtualizacaoMedicoDTO dados) {
        if(!dados.horariosDisponiveis().isEmpty()) {
            dados.horariosDisponiveis().forEach(horarios -> adicionarHorario(horarios.dia(),horarios.horaInicio(), horarios.horaFim()));
        }
    }

    public void excluir() {
        this.ativo = false;
    }

    public void adicionarHorario(DayOfWeek dia, LocalTime horaInicio, LocalTime horaFim) {
        HorarioDisponivel horario = new HorarioDisponivel(dia, horaInicio, horaFim, this);
        this.horariosDisponiveis.add(horario);
    }


}
