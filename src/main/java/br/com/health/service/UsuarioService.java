package br.com.health.service;

import br.com.health.dto.usuario.RegistrarDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioService {

    UserDetails findByLogin(String login);

    void save(RegistrarDTO data);
}
