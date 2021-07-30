package br.com.zupacademy.eduardo.mercadolivre.controller.response;

import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;

public class UsuarioResponse {
    private String login;

    public UsuarioResponse(Usuario usuarioLogado) {
        this.login = usuarioLogado.getUsername();
    }

    public String getLogin() {
        return login;
    }
}
