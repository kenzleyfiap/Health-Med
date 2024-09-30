package br.com.health.service.impl;

import br.com.health.domain.consulta.Consulta;
import br.com.health.domain.medico.HorarioDisponivel;
import br.com.health.domain.medico.Medico;
import br.com.health.domain.paciente.Paciente;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.consulta.ConsultaResponseDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.mapper.ConsultaMapper;
import br.com.health.repository.ConsultaRepository;
import br.com.health.service.ConsultaService;
import br.com.health.service.EmailService;
import br.com.health.service.MedicoService;
import br.com.health.service.PacienteService;
import br.com.health.validators.consulta.ValidadorAgendamentoDeConsulta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final ConsultaMapper mapper;
    private final List<ValidadorAgendamentoDeConsulta> validadores;
    private final MedicoService medicoService;
    private final PacienteService pacienteService;
    private final EmailService emailService;

    @Override
    public boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data) {
        return consultaRepository.existsByMedicoIdAndData(idMedico, data);
    }

    @Override
    public boolean existsByPacienteIdAndDataBetween(Long pacienteId, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario) {
        return consultaRepository.existsByPacienteIdAndDataBetween(pacienteId, primeiroHorario, ultimoHorario);
    }

    @Override
    @Transactional
    public ConsultaResponseDTO save(ConsultaDTO consultaDTO) {

        validarConsulta(consultaDTO);
        Medico medico = medicoService.getMedico(consultaDTO.idMedico());
        Paciente paciente = pacienteService.getPaciente(consultaDTO.idPaciente());

        Optional<HorarioDisponivel> horarioDisponivel = medicoService.verificarDisponibilidade(medico, consultaDTO.data());

        if(horarioDisponivel.isPresent()) {
            Consulta consulta = mapper.dtoToEntity(consultaDTO);
            consulta.setMedico(medico);
            consulta.setPaciente(paciente);

            Consulta consultaSaved = consultaRepository.save(consulta);
            medicoService.agendarConsulta(medico, consultaDTO.data());
            notificarMedicoConsultaAgendada(medico.getEmail(), medico.getNome(), paciente.getNome(), consultaSaved.getData());

            return mapper.entityToResponseDTO(consultaSaved);
        } else {
            throw new ValidacaoException("O médico não possui horários disponíveis para essa data.");
        }

    }

    private void validarConsulta(ConsultaDTO consultaDTO) {

        if (consultaDTO.idMedico() != null && Boolean.FALSE.equals(medicoService.existsById(consultaDTO.idMedico()))) {
            throw new ValidacaoException("Id do medico informado não existe");
        }

        if(consultaDTO.idMedico() != null && Boolean.FALSE.equals(pacienteService.existsById(consultaDTO.idPaciente()))) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        validadores.forEach(v -> v.validar(consultaDTO));
    }

    private void notificarMedicoConsultaAgendada(String emailMedico, String nomeMedico, String nomePaciente, LocalDateTime dataConsulta) {
        emailService.enviarEmailConsultaAgendada(emailMedico, nomeMedico, nomePaciente, dataConsulta);
    }
}
