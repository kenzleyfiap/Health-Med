package br.com.health.service;

import br.com.health.domain.usuario.PerfilUsuario;
import br.com.health.domain.usuario.Usuario;
import br.com.health.dto.usuario.RegistrarDTO;
import br.com.health.repository.UsuarioRepository;
import br.com.health.service.impl.UsuarioServiceImpl;
import br.com.health.utils.UsuarioHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UsuarioServiceTest {

    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioServiceImpl(usuarioRepository);
    }

    @Test
    void devePermitirBuscarPeloLogin() {
        // Mock do UserDetails
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("mockUser");
        when(mockUserDetails.getPassword()).thenReturn("mockPassword");

        // Configurando o mock para o repository
        when(usuarioRepository.findByLogin("mockUser")).thenReturn(mockUserDetails);

        // Chamando o método de serviço
        UserDetails result = usuarioService.findByLogin("mockUser");

        // Verificações
        assertNotNull(result);
        assertEquals("mockUser", result.getUsername());
        assertEquals("mockPassword", result.getPassword());

        verify(usuarioRepository, times(1)).findByLogin(any(String.class));

    }

    @Test
    void save() {
        RegistrarDTO data = new RegistrarDTO("teste", "teste", PerfilUsuario.ADMIN);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(UsuarioHelper.gerarUsuario());

        usuarioService.save(data);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}