package br.com.health.validators;


import br.com.health.dto.consulta.ConsultaDTO;

public interface ValidadorAgendamentoDeConsulta {
    void validar(ConsultaDTO dados);
}
