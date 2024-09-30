package br.com.health.service;

import java.time.LocalDateTime;

public interface EmailService {
    String enviarEmailTexto(String destinatario, String assunto, String mensagem);

    String enviarEmailConsultaAgendada(String destinatario, String nomeMedico, String nomePaciente, LocalDateTime dataConsulta);
}
