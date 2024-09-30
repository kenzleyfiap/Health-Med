package br.com.health.validators.consulta;


import br.com.health.dto.consulta.ConsultaDTO;

public interface ValidadorAgendamentoDeConsulta {
    void validar(ConsultaDTO dados);
}
