package br.com.zupacademy.eduardo.mercadolivre.controller.request.pagamento;

import br.com.zupacademy.eduardo.mercadolivre.clients.AcaoAposPagamentoClient;
import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.model.Compra;

public interface AcaoPagamento {

    void executa(AcaoAposPagamentoClient client, CentralEmail centralEmail, Compra compra);
}
