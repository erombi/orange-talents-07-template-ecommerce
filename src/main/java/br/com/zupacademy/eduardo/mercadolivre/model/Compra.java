package br.com.zupacademy.eduardo.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    @ManyToOne
    private Usuario comprador;

    @NotNull
    @Positive
    private BigDecimal precoUnitario;

    @NotNull
    private BigDecimal total;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusCompra status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GatewayPagamento gatewayPagamento;

    public Compra(Produto produto, Integer quantidade, Usuario comprador, BigDecimal precoUnitario, GatewayPagamento gatewayPagamento) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.comprador = comprador;
        this.precoUnitario = precoUnitario;
        this.status = StatusCompra.INICIADA;
        this.gatewayPagamento = gatewayPagamento;
        this.total = this.precoUnitario.multiply(new BigDecimal(quantidade));
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compra compra = (Compra) o;
        return Objects.equals(id, compra.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Usuario getVendedor() {
        return this.produto.getUsuario();
    }

    public String getUrlRedirecionamento(Long id, String urlConfirmacao) {
        return this.gatewayPagamento.getUrlRedirecionamento(id, urlConfirmacao);
    }
}
