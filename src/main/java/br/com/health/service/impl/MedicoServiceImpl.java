package br.com.health.service.impl;

import br.com.health.domain.*;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.medico.AtualizacaoMedicoDTO;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.dto.medico.MedicoResponseDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.mapper.HorarioDisponivelMapper;
import br.com.health.mapper.MedicoMapper;
import br.com.health.repository.MedicoRepository;
import br.com.health.service.MedicoService;
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

    @Override
    public Page<MedicoResponseDTO> findAllByAtivoTrue(Pageable pageable) {
        Page<Medico> medicos = repository.findAllByAtivoTrue(pageable);
        return medicos.map(mapper::entityToResponseDTO);
    }

    @Override
    public MedicoResponseDTO escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data) {
        return mapper.entityToResponseDTO(repository.escolherMedicoAleatorioLivreNaData(especialidade, data));
    }

    @Override
    public boolean findAtivoById(Long idMedico) {
        return findAtivoById(idMedico);
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

    public MedicoResponseDTO escolherMedico(ConsultaDTO dados) {
        if(dados.idMedico() != null) {
            return findById(dados.idMedico());
        }
        if(dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }
        return escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    @Override
    @Transactional
    public MedicoResponseDTO save(MedicoDTO medicoDTO) {
        Medico medico = mapper.dtoToEntity(medicoDTO);
        medico.getHorariosDisponiveis().forEach(horario -> horario.setMedico(medico));
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
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
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
