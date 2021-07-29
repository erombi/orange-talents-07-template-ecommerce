package br.com.zupacademy.eduardo.mercadolivre.controller;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.CategoriaRequest;
import br.com.zupacademy.eduardo.mercadolivre.model.Categoria;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> insert(@RequestBody @Valid CategoriaRequest request) {
        Categoria categoria = request.toModel(manager);
        manager.persist(categoria);

        return ResponseEntity.ok().build();
    }
}
