package br.com.zupacademy.eduardo.mercadolivre.model;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.pagamento.StatusPagamento;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Long quantidade;

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

    @OneToMany(mappedBy = "compra")
    private Set<Pagamento> pagamentos = new HashSet<>();

    @Deprecated
    public Compra() {
    }

    public Compra(Produto produto, Long quantidade, Usuario comprador, BigDecimal precoUnitario, GatewayPagamento gatewayPagamento) {
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

    public Usuario getComprador() {
        return comprador;
    }

    public String getUrlRedirecionamento() {
        return this.gatewayPagamento.getUrlRedirecionamento(this.id);
    }

    public String getUrlConfirmacaoPagamento(Long id) {
        return this.gatewayPagamento.getUrlConfirmacaoPagamento(id);
    }

    public void finaliza(Pagamento pagamento) {
        Assert.state(this.status == StatusCompra.INICIADA, "Somente compras INICIADAS podem ser finalizadas !");

        if (pagamento.getStatus() == StatusPagamento.ERRO)
            return;

        this.status = StatusCompra.FINALIZADA;
    }

    public boolean statusValido() {
        return this.status == StatusCompra.INICIADA;
    }
}
