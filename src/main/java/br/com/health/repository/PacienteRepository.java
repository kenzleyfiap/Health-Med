package br.com.health.repository;

import br.com.health.domain.paciente.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            select p.ativo
            from Paciente p
            where
            p.id = :idPaciente
            """)
    boolean findAtivoById(Long idPaciente);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
