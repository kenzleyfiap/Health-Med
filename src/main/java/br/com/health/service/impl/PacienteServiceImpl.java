package br.com.health.service.impl;

import br.com.health.domain.Medico;
import br.com.health.domain.Paciente;
import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.mapper.PacienteMapper;
import br.com.health.repository.PacienteRepository;
import br.com.health.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository repository;
    private final PacienteMapper mapper;

    @Override
    public boolean findAtivoById(Long idPaciente) {
        return repository.findAtivoById(idPaciente);
    }

    @Override
    public Page<PacienteResponseDTO> findAllByAtivoTrue(Pageable paginacao) {
        Page<Paciente> pacientes = repository.findAllByAtivoTrue(paginacao);
        return pacientes.map(mapper::entityToResponseDTO);
    }

    @Override
    public boolean existsById(Long idPaciente) {
        return repository.existsById(idPaciente);
    }

    @Override
    public PacienteResponseDTO getReferenceById(Long idPaciente) {
        Paciente paciente = repository.findById(idPaciente).orElseThrow(() -> new ValidacaoException("Paciente não encontrado"));
        return mapper.entityToResponseDTO(paciente);
    }

    @Override
    @Transactional
    public PacienteResponseDTO save(PacienteDTO pacienteDTO) {
        Paciente paciente = mapper.dtoToEntity(pacienteDTO);
        paciente.setAtivo(Boolean.TRUE);
        return mapper.entityToResponseDTO(repository.save(paciente));
    }

    public Paciente getPaciente(Long id) {
        return repository.findById(id).orElseThrow(() -> new ValidacaoException("Paciente não encontrado"));
    }

}
