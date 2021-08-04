package br.com.zupacademy.eduardo.mercadolivre.controller;

import br.com.zupacademy.eduardo.mercadolivre.clients.AcaoAposPagamentoClient;
import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.PagamentoPagSeguroRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.PagamentoPaypalRequest;
import br.com.zupacademy.eduardo.mercadolivre.infra.ExecuteTransaction;
import br.com.zupacademy.eduardo.mercadolivre.model.Compra;
import br.com.zupacademy.eduardo.mercadolivre.model.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/compras")
public class PagamentoController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ExecuteTransaction executor;

    @Autowired
    private AcaoAposPagamentoClient client;

    @Autowired
    private CentralEmail centralEmail;

    @PostMapping("/{id}/pagamentos/paypal")
    public ResponseEntity<?> pagamentoPaypal(@RequestBody @Valid PagamentoPaypalRequest request, @PathVariable Long id) {
        Compra compra = manager.find(Compra.class, id);

        if (!compra.statusValido()) return ResponseEntity
                .unprocessableEntity().body(getMap("status", "Somente compras com status INICIADO podem ser pagos !"));
        Pagamento pagamento = request.toModel(compra);

        executor.inTransaction(() -> {
            compra.finaliza(pagamento);
            manager.persist(pagamento);
        });

        request.executaAcoes(client, centralEmail, compra);
        return ResponseEntity.ok(new HashMap<String, String>().put("statusPagamento", pagamento.getStatus().toString()));
    }

    @PostMapping("/{id}/pagamentos/pagseguro")
    public ResponseEntity<?> pagamentoPagSeguro(@RequestBody @Valid PagamentoPagSeguroRequest request, @PathVariable Long id) {
        Compra compra = manager.find(Compra.class, id);

        if (!compra.statusValido()) return ResponseEntity
                .unprocessableEntity().body(getMap("status", "Somente compras com status INICIADO podem ser pagos !"));
        Pagamento pagamento = request.toModel(compra);

        executor.inTransaction(() -> {
            compra.finaliza(pagamento);
            manager.persist(pagamento);
        });

        request.executaAcoes(client, centralEmail, compra);
        return ResponseEntity.ok(getMap("statusPagamento", pagamento.getStatus().toString()));
    }

    private Map<String, String> getMap(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
