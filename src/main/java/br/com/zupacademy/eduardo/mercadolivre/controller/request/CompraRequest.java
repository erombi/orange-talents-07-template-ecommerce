package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation.ExistsId;
import br.com.zupacademy.eduardo.mercadolivre.model.Compra;
import br.com.zupacademy.eduardo.mercadolivre.model.GatewayPagamento;
import br.com.zupacademy.eduardo.mercadolivre.model.Produto;
import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraRequest {

    @NotNull
    private GatewayPagamento gatewayPagamento;

    @NotNull
    @Positive
    @ExistsId(clazz = Produto.class, field = "id")
    private Long idProduto;

    @NotNull
    @Positive
    private Long quantidade;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CompraRequest(GatewayPagamento gatewayPagamento, Long idProduto, Long quantidade) {
        this.gatewayPagamento = gatewayPagamento;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public Compra toModel(Produto produto, Usuario usuario) {
        return new Compra(produto, this.quantidade, usuario, produto.getValor(), this.gatewayPagamento);
    }

    public Long getIdProduto() {
        return this.idProduto;
    }

    public Long getQuantidade() {
        return this.quantidade;
    }

    public GatewayPagamento getGatewayPagamento() {
        return gatewayPagamento;
    }
}
