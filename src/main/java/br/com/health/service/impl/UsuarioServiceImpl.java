package br.com.health.service.impl;

import br.com.health.domain.usuario.Usuario;
import br.com.health.dto.usuario.RegistrarDTO;
import br.com.health.repository.UsuarioRepository;
import br.com.health.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;


    @Override
    public UserDetails findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    @Override
    public void save(RegistrarDTO data) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUsuario = new Usuario(data.login(), encryptedPassword, data.role());
        usuarioRepository.save(newUsuario);
    }
}
