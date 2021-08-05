package br.com.zupacademy.eduardo.mercadolivre.controller;

import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.CompraRequest;
import br.com.zupacademy.eduardo.mercadolivre.model.Compra;
import br.com.zupacademy.eduardo.mercadolivre.model.Produto;
import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private EntityManager manager;
    private CentralEmail centralEmail;

    public CompraController(EntityManager manager, CentralEmail centralEmail) {
        this.manager = manager;
        this.centralEmail = centralEmail;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> novaCompra(@RequestBody @Valid CompraRequest request, @AuthenticationPrincipal Usuario usuario) {
        Produto produto = manager.find(Produto.class, request.getIdProduto());
        boolean abatido = produto.abateEstoque(request.getQuantidade());

        if (abatido) {
            Compra compra = request.toModel(produto, usuario);
            manager.persist(compra);
            centralEmail.enviaNovaCompra(compra);

            String urlRedirecionamento = compra.getUrlRedirecionamento();

            try {
                URI uri = new URI(urlRedirecionamento);
                return ResponseEntity
                            .status(HttpStatus.FOUND)
                            .location(uri)
                        .build();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.unprocessableEntity().build();
    }

}
