package br.com.zupacademy.eduardo.mercadolivre.controller.request.pagamento;

import br.com.zupacademy.eduardo.mercadolivre.clients.AcaoAposPagamentoClient;
import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.model.Compra;

import java.util.HashMap;

public class ComunicaRankingVendedor implements AcaoPagamento {
    @Override
    public void executa(AcaoAposPagamentoClient client, CentralEmail centralEmail, Compra compra) {
        client.rankingVendedores(getRequest(compra));
    }

    private HashMap<String, String> getRequest(Compra compra) {
        HashMap<String, String> request = new HashMap<>();
        request.put("compra", compra.getId().toString());
        return request;
    }
}
