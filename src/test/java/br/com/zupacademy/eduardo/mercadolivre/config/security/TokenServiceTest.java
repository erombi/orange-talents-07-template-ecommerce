package br.com.zupacademy.eduardo.mercadolivre.config.security;

import br.com.zupacademy.eduardo.mercadolivre.builder.UsuarioBuilder;
import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenServiceTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() throws Exception {
        usuario = new UsuarioBuilder()
                            .withId(1L)
                            .withLogin("teste@zup.com")
                            .withSenha("123456")
                        .build();
    }

    @Test
    @DisplayName("Deve retornar token o ID do usuário")
    public void test1() {
        TokenService tokenService = new TokenService("86400", "test-enviroment");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(usuario);

        String token = tokenService.gerarToken(authentication);

        assertNotNull(token);

        boolean tokenValido = tokenService.isTokenValido(token);

        assertTrue(tokenValido);

        Long idUsuario = tokenService.getIdUsuario(token);

        assertEquals(usuario.getId(), idUsuario);
    }
}
