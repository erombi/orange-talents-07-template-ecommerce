package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import br.com.zupacademy.eduardo.mercadolivre.clients.AcaoAposPagamentoClient;
import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation.DuplicatedValue;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation.ExistsId;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.pagamento.StatusPagamento;
import br.com.zupacademy.eduardo.mercadolivre.model.Compra;
import br.com.zupacademy.eduardo.mercadolivre.model.Pagamento;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagamentoPagSeguroRequest {

    @NotBlank
    private String idPagamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PagamentoPagSeguroRequest(String idPagamento, StatusPagamento status) {
        this.idPagamento = idPagamento;
        this.status = status;
    }

    public Pagamento toModel(Compra compra) {
        return new Pagamento(this.idPagamento, compra, this.status);
    }

    public void executaAcoes(AcaoAposPagamentoClient client, CentralEmail centralEmail, Compra compra) {
        this.status.executaAcoes(client, centralEmail, compra);
    }
}
