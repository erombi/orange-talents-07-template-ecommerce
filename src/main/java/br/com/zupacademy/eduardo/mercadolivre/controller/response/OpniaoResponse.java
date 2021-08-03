package br.com.zupacademy.eduardo.mercadolivre.controller.response;

import br.com.zupacademy.eduardo.mercadolivre.model.Opniao;

public class OpniaoResponse {

    private Integer nota;
    private String titulo;
    private String descricao;
    private UsuarioResponse usuario;

    public OpniaoResponse(Opniao opniao) {
        this.nota = opniao.getNota();
        this.titulo = opniao.getTitulo();
        this.descricao = opniao.getDescricao();
        this.usuario = new UsuarioResponse(opniao.getUsuario());
    }

    public Integer getNota() {
        return nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }
}
