package br.com.health.service;

import br.com.health.domain.usuario.PerfilUsuario;
import br.com.health.domain.usuario.Usuario;
import br.com.health.dto.usuario.RegistrarDTO;
import br.com.health.repository.UsuarioRepository;
import br.com.health.service.impl.UsuarioServiceImpl;
import br.com.health.utils.UsuarioHelper;
import jakarta.transaction.Transactional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class UsuarioServiceIT {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Test
    void devePermitirBuscarPeloLogin() {

        UserDetails usuario = usuarioService.findByLogin("usuario1");

        assertNotNull(usuario);

        assertThat(usuario)
                .isNotNull()
                .isInstanceOf(UserDetails.class);

        assertThat(usuario.getUsername()).isNotNull();
        assertThat(usuario.getPassword()).isNotNull();
    }

    @Test
    void devePermitirSalvar() {
        RegistrarDTO data = new RegistrarDTO("teste", "teste", PerfilUsuario.ADMIN);

        usuarioService.save(data);

        UserDetails login = usuarioService.findByLogin(data.login());

        assertThat(login)
                .isNotNull()
                .isInstanceOf(UserDetails.class);

        assertThat(login.getUsername()).isNotNull();
        assertThat(login.getPassword()).isNotNull();

    }
}