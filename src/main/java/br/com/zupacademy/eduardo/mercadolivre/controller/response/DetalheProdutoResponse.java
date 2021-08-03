package br.com.zupacademy.eduardo.mercadolivre.controller.response;

import br.com.zupacademy.eduardo.mercadolivre.model.Produto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DetalheProdutoResponse {

    private String nome;
    private BigDecimal valor;
    private Long quantidadeDisponivel;
    private String descricao;
    private String categoria;
    private Set<CaracteristicaResponse> caracteristicas = new HashSet<>();
    private Set<ImagemProdutoResponse> linksImagem = new HashSet<>();
    private Set<OpniaoResponse> opnioes = new HashSet<>();
    private Set<PerguntaResponse> perguntas = new HashSet<>();
    private Double mediaOpniao;

    public DetalheProdutoResponse(Produto produto) {
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.quantidadeDisponivel = produto.getQuantidadeDisponivel();
        this.descricao = produto.getDescricao();
        this.categoria = produto.montaStringCategorias();
        this.caracteristicas = produto.getCaracteristicas().stream().map(CaracteristicaResponse::new).collect(Collectors.toSet());
        this.linksImagem = produto.getImagens().stream().map(ImagemProdutoResponse::new).collect(Collectors.toSet());
        this.opnioes = produto.getOpnioes().stream().map(OpniaoResponse::new).collect(Collectors.toSet());
        this.perguntas = produto.getPerguntas().stream().map(PerguntaResponse::new).collect(Collectors.toSet());
        this.mediaOpniao = produto.calculaOpniaoMedia();
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Long getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public Set<CaracteristicaResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<ImagemProdutoResponse> getLinksImagem() {
        return linksImagem;
    }

    public Set<OpniaoResponse> getOpnioes() {
        return opnioes;
    }

    public Double getMediaOpniao() {
        return mediaOpniao;
    }

    public Set<PerguntaResponse> getPerguntas() {
        return perguntas;
    }
}
