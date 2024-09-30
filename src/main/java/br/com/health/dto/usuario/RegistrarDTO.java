package br.com.health.dto.usuario;

import br.com.health.domain.usuario.PerfilUsuario;

public record RegistrarDTO(String login, String password, PerfilUsuario role) {
}
