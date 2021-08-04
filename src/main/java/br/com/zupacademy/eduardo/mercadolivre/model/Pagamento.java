package br.com.zupacademy.eduardo.mercadolivre.model;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.pagamento.StatusPagamento;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String idPagamento;

    @ManyToOne
    private Compra compra;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    private Instant criadoEm = Instant.now();

    @Deprecated
    public Pagamento() { }

    public Pagamento(String idPagamento, Compra compra, StatusPagamento status) {
        this.idPagamento = idPagamento;
        this.compra = compra;
        this.status = status;
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(idPagamento, pagamento.idPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPagamento);
    }
}
