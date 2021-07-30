package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public LoginRequest(@NotBlank String login, @NotBlank String senha) {
        this.login = login;
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(login, senha);
    }
}
