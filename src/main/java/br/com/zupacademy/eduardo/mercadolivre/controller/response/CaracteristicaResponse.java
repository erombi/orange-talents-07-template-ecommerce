package br.com.zupacademy.eduardo.mercadolivre.controller.response;

import br.com.zupacademy.eduardo.mercadolivre.model.Caracteristica;

public class CaracteristicaResponse {

    private String nome;
    private String descricao;

    public CaracteristicaResponse(Caracteristica caracteristica) {
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
