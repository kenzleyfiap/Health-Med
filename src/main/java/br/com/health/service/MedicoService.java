package br.com.health.service;

import br.com.health.domain.Especialidade;
import br.com.health.domain.HorarioDisponivel;
import br.com.health.domain.Medico;
import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.medico.AtualizacaoMedicoDTO;
import br.com.health.dto.medico.MedicoDTO;
import br.com.health.dto.medico.MedicoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MedicoService {

    Page<MedicoResponseDTO> findAllByAtivoTrue(Pageable pageable);

    MedicoResponseDTO escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

    boolean findAtivoById(Long idMedico);

    boolean existsById(Long idMedico);

    MedicoResponseDTO findById(Long idMedico);

    MedicoResponseDTO escolherMedico(ConsultaDTO dados);

    MedicoResponseDTO save(MedicoDTO medico);

    MedicoResponseDTO updateHorarioDisponiveis(AtualizacaoMedicoDTO medicoDTO);

    void delete(Long id);

    Medico getMedico(Long id);

    Optional<HorarioDisponivel> verificarDisponibilidade(Medico medico, LocalDateTime dataConsulta);

    void agendarConsulta(Medico medico, LocalDateTime dataConsulta);

}
