package br.com.health.service.impl;

import br.com.health.domain.paciente.Paciente;
import br.com.health.domain.usuario.PerfilUsuario;
import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;
import br.com.health.dto.usuario.RegistrarDTO;
import br.com.health.infra.exception.ValidacaoException;
import br.com.health.mapper.PacienteMapper;
import br.com.health.repository.PacienteRepository;
import br.com.health.service.PacienteService;
import br.com.health.service.UsuarioService;
import br.com.health.validators.paciente.ValidadorCadastroPaciente;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository repository;
    private final PacienteMapper mapper;
    private final UsuarioService usuarioService;
    private final List<ValidadorCadastroPaciente> validadorCadastroPaciente;

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
        validadorCadastroPaciente.forEach(validador -> validador.validar(pacienteDTO));
        Paciente paciente = mapper.dtoToEntity(pacienteDTO);
        paciente.setAtivo(Boolean.TRUE);
        usuarioService.save(new RegistrarDTO(pacienteDTO.email(), pacienteDTO.password(), PerfilUsuario.PACIENTE));
        return mapper.entityToResponseDTO(repository.save(paciente));
    }

    public Paciente getPaciente(Long id) {
        return repository.findById(id).orElseThrow(() -> new ValidacaoException("Paciente não encontrado"));
    }


}
