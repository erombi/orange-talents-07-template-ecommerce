package br.com.zupacademy.eduardo.mercadolivre.config.security;

import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class TokenService {

    @Value("${mercadolivre.jwt.expiration}")
    private String expiration;

    @Value("${mercadolivre.jwt.secret}")
    private String secret;

    @Autowired
    private Environment environment;

    public TokenService() { }

    /*
    * Somente em ambiente de teste
    * */
    public TokenService(String expiration, String secret) {
        if (environment != null) {
            Assert.state(environment.getActiveProfiles()[0].equals("test"), "Esse construtor deve ser usado SOMENTE em ambiente de test");
        }

        this.expiration = expiration;
        this.secret = secret;
    }

    public String gerarToken(Authentication authentication) {
        Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                    .setIssuer("API Mercado Livre")
                    .setSubject(logado.getId().toString())
                    .setIssuedAt(hoje)
                    .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
