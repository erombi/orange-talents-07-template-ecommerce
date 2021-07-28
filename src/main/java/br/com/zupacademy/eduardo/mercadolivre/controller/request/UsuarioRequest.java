package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import br.com.zupacademy.eduardo.mercadolivre.model.Password;
import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UsuarioRequest {

    @Email
    @NotBlank
    private String login;

    @Length(min = 6)
    @NotBlank
    private String senha;

    @Deprecated
    public UsuarioRequest() { }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UsuarioRequest(@Email @NotBlank String login, @NotBlank @Length(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public Usuario toModel() {
        return new Usuario(this.login, new Password(this.senha));
    }

}
