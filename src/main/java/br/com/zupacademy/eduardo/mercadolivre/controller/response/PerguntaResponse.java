package br.com.zupacademy.eduardo.mercadolivre.controller.response;

import br.com.zupacademy.eduardo.mercadolivre.model.Pergunta;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public class PerguntaResponse {

    private String titulo;
    private String produto;
    private Instant criadoEm;

    public PerguntaResponse(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
        this.produto = pergunta.getProduto().getNome();
        this.criadoEm = pergunta.getCriadoEm();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getProduto() {
        return produto;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }
}
