package br.com.zupacademy.eduardo.mercadolivre.controller;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.ProdutoRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.validator.QuantidadeMinimaCaracteristicasValidator;
import br.com.zupacademy.eduardo.mercadolivre.infra.ExecuteTransaction;
import br.com.zupacademy.eduardo.mercadolivre.model.Produto;
import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new QuantidadeMinimaCaracteristicasValidator());
    }

    @Autowired
    private ExecuteTransaction executor;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid ProdutoRequest request, @AuthenticationPrincipal Usuario usuario) {
        Produto produto = request.toModel(manager, usuario);

        executor.inTransaction(() -> {
            manager.persist(produto);
        });

        return ResponseEntity.ok().build();
    }
}
