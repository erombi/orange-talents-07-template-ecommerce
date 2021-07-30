package br.com.zupacademy.eduardo.mercadolivre.builder;

import br.com.zupacademy.eduardo.mercadolivre.model.Password;
import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;

public class UsuarioBuilder {

    private Long id;
    private String login;
    private String senha;

    public UsuarioBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UsuarioBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public UsuarioBuilder withSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public Usuario build() {
        return new Usuario(id, login, senha);
    }
}
