package br.com.zupacademy.eduardo.mercadolivre.controller;

import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.component.UploaderFake;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.ImagemRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.OpniaoRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.PerguntaRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.ProdutoRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation.ExistsId;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.validator.QuantidadeMinimaCaracteristicasValidator;
import br.com.zupacademy.eduardo.mercadolivre.controller.response.PerguntaResponse;
import br.com.zupacademy.eduardo.mercadolivre.infra.ExecuteTransaction;
import br.com.zupacademy.eduardo.mercadolivre.model.Opniao;
import br.com.zupacademy.eduardo.mercadolivre.model.Pergunta;
import br.com.zupacademy.eduardo.mercadolivre.model.Produto;
import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @PersistenceContext
    private EntityManager manager;

    @InitBinder(value = "produtoRequest")
    public void init(WebDataBinder binder) {
        binder.addValidators(new QuantidadeMinimaCaracteristicasValidator());
    }

    @Autowired
    private ExecuteTransaction executor;

    @Autowired
    private UploaderFake uploaderFake;

    @Autowired
    private CentralEmail centralEmail;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody @Valid ProdutoRequest request, @AuthenticationPrincipal Usuario usuario) {
        Produto produto = request.toModel(manager, usuario);

        executor.inTransaction(() -> {
            manager.persist(produto);
        });

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/imagens")
    public ResponseEntity<?> uploadImage(@PathVariable @ExistsId(clazz = Produto.class, field = "id") Long id,
                                            @Valid ImagemRequest request, @AuthenticationPrincipal Usuario usuario) {
        Produto produto = manager.find(Produto.class, id);

        if (produto == null) return ResponseEntity.notFound().build();
        if (!produto.pertenceAoDono(usuario)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Set<String> links = uploaderFake.upload(request.getFiles());
        produto.associaImagens(links);

        executor.inTransaction(() -> {
            manager.merge(produto);
        });

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/opnioes")
    public ResponseEntity<?> insertOpniao(@PathVariable Long id, @RequestBody @Valid OpniaoRequest request, @AuthenticationPrincipal Usuario usuario) {
        Produto produto = manager.find(Produto.class, id);

        if (produto == null) return ResponseEntity.notFound().build();
        if (!produto.pertenceAoDono(usuario)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Opniao opniao = request.toModel(produto, usuario);

        executor.inTransaction(() -> {
            manager.persist(opniao);
        });

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/perguntas")
    public ResponseEntity<?> insertPergunta(@PathVariable Long id, @RequestBody @Valid PerguntaRequest request, @AuthenticationPrincipal Usuario usuario) {
        Produto produto = manager.find(Produto.class, id);

        if (produto == null) return ResponseEntity.notFound().build();

        Pergunta pergunta = request.toModel(produto, usuario);

        executor.inTransaction(() -> {
            manager.persist(pergunta);
        });

        centralEmail.enviaPerguntaAoVendedor(pergunta);

        return ResponseEntity.ok(new PerguntaResponse(pergunta));
    }
}
