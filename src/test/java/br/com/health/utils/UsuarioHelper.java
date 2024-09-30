package br.com.health.utils;

import br.com.health.domain.usuario.PerfilUsuario;
import br.com.health.domain.usuario.Usuario;

public abstract class UsuarioHelper {

    public static Usuario gerarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRole(PerfilUsuario.ADMIN);
        usuario.setPassword("password");
        usuario.setLogin("login");

        return usuario;
    }
}
