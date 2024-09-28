package br.com.health.repository;

import br.com.health.domain.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    // Verifica se já existe uma consulta no exato mesmo horário
    @Query("SELECT COUNT(c) > 0 FROM Consulta c WHERE c.medico.id = :idMedico AND c.data = :data")
    boolean existsByMedicoIdAndData(@Param("idMedico") Long idMedico, @Param("data") LocalDateTime data);

    // Verifica se existe consulta do paciente em um intervalo de tempo
    @Query("SELECT COUNT(c) > 0 FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.data BETWEEN :primeiroHorario AND :ultimoHorario")
    boolean existsByPacienteIdAndDataBetween(@Param("pacienteId") Long pacienteId, @Param("primeiroHorario") LocalDateTime primeiroHorario, @Param("ultimoHorario") LocalDateTime ultimoHorario);
}

