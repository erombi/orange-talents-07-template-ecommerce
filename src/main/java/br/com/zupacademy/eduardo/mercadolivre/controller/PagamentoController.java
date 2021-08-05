package br.com.zupacademy.eduardo.mercadolivre.controller;

import br.com.zupacademy.eduardo.mercadolivre.clients.AcaoAposPagamentoClient;
import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.PagamentoPagSeguroRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.PagamentoPaypalRequest;
import br.com.zupacademy.eduardo.mercadolivre.infra.ExecuteTransaction;
import br.com.zupacademy.eduardo.mercadolivre.model.Compra;
import br.com.zupacademy.eduardo.mercadolivre.model.Pagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/compras")
public class PagamentoController {

    private EntityManager manager;
    private ExecuteTransaction executor;
    private AcaoAposPagamentoClient client;
    private CentralEmail centralEmail;

    public PagamentoController(EntityManager manager, ExecuteTransaction executor, AcaoAposPagamentoClient client, CentralEmail centralEmail) {
        this.manager = manager;
        this.executor = executor;
        this.client = client;
        this.centralEmail = centralEmail;
    }

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
