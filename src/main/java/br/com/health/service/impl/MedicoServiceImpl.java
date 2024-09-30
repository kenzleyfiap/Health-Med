package br.com.health.service.impl;

import br.com.health.domain.medico.Especialidade;
import br.com.health.domain.medico.HorarioDisponivel;
import br.com.health.domain.medico.Medico;
import br.com.health.domain.usuario.PerfilUsuario;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.medico.AtualizacaoMedicoDTO;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.dto.medico.MedicoResponseDTO;
import br.com.health.dto.usuario.RegistrarDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.mapper.HorarioDisponivelMapper;
import br.com.health.mapper.MedicoMapper;
import br.com.health.repository.MedicoRepository;
import br.com.health.service.MedicoService;
import br.com.health.service.UsuarioService;
import br.com.health.validators.medico.ValidadorCadastroMedico;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository repository;
    private final MedicoMapper mapper;
    private final HorarioDisponivelMapper horarioDisponivelMapper;
    private final UsuarioService usuarioService;
    private final List<ValidadorCadastroMedico> validadorCadastroMedicos;

    @Override
    public Page<MedicoResponseDTO> findAllByAtivoTrue(Pageable pageable) {
        Page<Medico> medicos = repository.findAllByAtivoTrue(pageable);
        return medicos.map(mapper::entityToResponseDTO);
    }

    @Override
    public boolean existsById(Long idMedico) {
        return repository.existsById(idMedico);
    }

    @Override
    @Transactional
    public MedicoResponseDTO findById(Long idMedico) {
        return mapper.entityToResponseDTO(repository.findById(idMedico).orElseThrow(() -> new ValidacaoException("médico não encontrado")));
    }

    @Override
    @Transactional
    public MedicoResponseDTO save(MedicoDTO medicoDTO) {
        validadorCadastroMedicos.forEach(validador -> validador.validar(medicoDTO));
        Medico medico = mapper.dtoToEntity(medicoDTO);
        medico.getHorariosDisponiveis().forEach(horario -> horario.setMedico(medico));
        medico.setAtivo(Boolean.TRUE);
        usuarioService.save(new RegistrarDTO(medico.getEmail(), medicoDTO.password(), PerfilUsuario.MEDICO));
        return mapper.entityToResponseDTO(repository.save(medico));
    }

    @Override
    @Transactional
    public MedicoResponseDTO updateHorarioDisponiveis(AtualizacaoMedicoDTO medicoDTO) {
        Medico medico = repository.findById(medicoDTO.id())
                .orElseThrow(() -> new ValidacaoException("Médico não encontrado"));

        // Lista atual de horários
        List<HorarioDisponivel> horariosAtuais = medico.getHorariosDisponiveis();

        // Lista de novos horários a partir do DTO
        List<HorarioDisponivel> novosHorarios = horarioDisponivelMapper.dtoToEntity(medicoDTO.horariosDisponiveis());

        // Adiciona o médico a cada novo horário
        novosHorarios.forEach(horario -> horario.setMedico(medico));

        // Remover horários antigos que não estão na nova lista
        horariosAtuais.removeIf(horarioAtual ->
                novosHorarios.stream().noneMatch(novoHorario -> novoHorario.getId() != null && novoHorario.getId().equals(horarioAtual.getId()))
        );

        // Adiciona novos horários ou atualiza os existentes
        novosHorarios.forEach(novoHorario -> {
            if (novoHorario.getId() == null ||
                    horariosAtuais.stream().noneMatch(h -> h.getId().equals(novoHorario.getId()))) {
                medico.getHorariosDisponiveis().add(novoHorario); // Adiciona novos
            } else {
                // Atualiza horários existentes
                horariosAtuais.stream()
                        .filter(horarioAtual -> horarioAtual.getId().equals(novoHorario.getId()))
                        .forEach(horarioAtual -> {
                            horarioAtual.setDia(novoHorario.getDia());
                            horarioAtual.setHoraInicio(novoHorario.getHoraInicio());
                            horarioAtual.setHoraFim(novoHorario.getHoraFim());
                        });
            }
        });

        // Salva o médico com a coleção de horários atualizada
        return mapper.entityToResponseDTO(repository.save(medico));
    }

    @Override
    public Medico getMedico(Long id) {
        return repository.findById(id).orElseThrow(() -> new ValidacaoException("Medico não encontrado"));
    }

    @Override
    public Optional<HorarioDisponivel> verificarDisponibilidade(Medico medico, LocalDateTime dataConsulta) {
        DayOfWeek diaConsulta = dataConsulta.getDayOfWeek();
        LocalTime horaConsulta = dataConsulta.toLocalTime();

        return medico.getHorariosDisponiveis().stream()
                .filter(h -> h.getDia().equals(diaConsulta) &&
                        !horaConsulta.isBefore(h.getHoraInicio()) &&
                        !horaConsulta.isAfter(h.getHoraFim()))
                .findFirst();  // Retorna o horário disponível que coincide com a consulta
    }
    @Override
    public void agendarConsulta(Medico medico, LocalDateTime dataConsulta) {
        Optional<HorarioDisponivel> horarioDisponivel = verificarDisponibilidade(medico, dataConsulta);

            // Remover o horário disponível após a consulta
            medico.getHorariosDisponiveis().remove(horarioDisponivel.get());
            repository.save(medico);  // Salva a alteração na lista de horários
        }

}
