package br.com.health.domain.usuario;

import lombok.Getter;

@Getter
public enum PerfilUsuario {

    ADMIN("admin"),
    MEDICO("medico"),
    PACIENTE("paciente");

    private String perfil;

    PerfilUsuario(String perfil) {
        this.perfil = perfil;
    }

}
