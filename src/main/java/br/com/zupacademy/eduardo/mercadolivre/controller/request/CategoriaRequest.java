package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation.ExistsId;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation.UniqueValue;
import br.com.zupacademy.eduardo.mercadolivre.model.Categoria;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class CategoriaRequest {

    @UniqueValue(clazz = Categoria.class, field = "nome")
    @NotBlank
    private String nome;

    @ExistsId(clazz = Categoria.class, field = "id")
    private Long idCategoria;

    public CategoriaRequest(@NotBlank String nome, Long idCategoria) {
        this.nome = nome;
        this.idCategoria = idCategoria;
    }

    public Categoria toModel(EntityManager manager) {
        Categoria categoria = new Categoria(this.nome);

        if (idCategoria != null) categoria.setCategoriaMae(manager.find(Categoria.class, idCategoria));

        return categoria;
    }
}
