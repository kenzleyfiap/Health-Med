package br.com.health.service.impl;

import br.com.health.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String remetente;

    @Override
    public String enviarEmailTexto(String destinatario, String assunto, String mensagem) {
        try{

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(mensagem);

            javaMailSender.send(simpleMailMessage);

            return "Email enviado";

        } catch (Exception exception) {
            return "Erro ao enviar e-mail";
        }
    }

    @Override
    public String enviarEmailConsultaAgendada(String destinatario, String nomeMedico, String nomePaciente, LocalDateTime dataConsulta) {
        String assunto = "Health&Med - Nova consulta agendada";
        String mensagem = montarCorpoEmailConsulta(nomeMedico, nomePaciente, dataConsulta);
        return enviarEmailTexto(destinatario, assunto, mensagem);
    }

    private String montarCorpoEmailConsulta(String nomeMedico, String nomePaciente, LocalDateTime dataConsulta) {
        // Formatando data e horário
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");

        String dataFormatada = dataConsulta.format(formatterData);
        String horarioFormatado = dataConsulta.format(formatterHora);

        return String.format(
                "Olá, Dr. %s!\n\n" +
                        "Você tem uma nova consulta marcada!\n" +
                        "Paciente: %s.\n" +
                        "Data e horário: %s às %s.",
                nomeMedico, nomePaciente, dataFormatada, horarioFormatado
        );
    }
}
