package br.com.zupacademy.eduardo.mercadolivre.controller.request.pagamento;

import br.com.zupacademy.eduardo.mercadolivre.clients.AcaoAposPagamentoClient;
import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.model.Compra;

import java.util.Set;

public enum StatusPagamento {

    ERRO(Set.of(new EnviaEmaiErroPagamento())),
    SUCESSO(Set.of(new ComunicaNotaFiscal(), new ComunicaRankingVendedor(), new EnviaEmailConfirmaPagamento()));

    private Set<AcaoPagamento> acoes;

    StatusPagamento(Set<AcaoPagamento> acoes) {
        this.acoes = acoes;
    }

    public void executaAcoes(AcaoAposPagamentoClient client, CentralEmail centralEmail, Compra compra) {
        acoes.forEach(a -> {
            a.executa(client, centralEmail, compra);
        });
    }

}
