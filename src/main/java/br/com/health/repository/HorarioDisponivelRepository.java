package br.com.health.repository;

import br.com.health.domain.medico.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
}
