package br.com.zupacademy.eduardo.mercadolivre.controller.request.pagamento;

import br.com.zupacademy.eduardo.mercadolivre.clients.AcaoAposPagamentoClient;
import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.model.Compra;

public class EnviaEmaiErroPagamento implements AcaoPagamento {
    @Override
    public void executa(AcaoAposPagamentoClient client, CentralEmail centralEmail, Compra compra) {
        centralEmail.enviaErroPagamento(compra);
    }
}
