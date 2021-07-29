package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import br.com.zupacademy.eduardo.mercadolivre.model.Categoria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;

public class CategoriaRequestTest {

    @Test
    @DisplayName("Deve retornar Categoria com categoriaMae null")
    public void test1() {
        EntityManager manager = Mockito.mock(EntityManager.class);

        CategoriaRequest brinquedos = new CategoriaRequest("Brinquedos", null);
        Categoria categoria = brinquedos.toModel(manager);

        Assertions.assertEquals("Brinquedos", categoria.getNome());
        Assertions.assertNull(categoria.getCategoriaMae());
    }

    @Test
    @DisplayName("Deve retornar Categoria com categoria nao nula")
    public void test2() {
        Long idCategoria = 1L;

        EntityManager manager = Mockito.mock(EntityManager.class);
        Mockito.when(manager.find(Categoria.class, idCategoria)).thenReturn(new Categoria("Crianças"));

        Categoria brinquedo = new CategoriaRequest("Brinquedo", idCategoria).toModel(manager);

        Assertions.assertEquals("Brinquedo", brinquedo.getNome());
        Assertions.assertEquals("Crianças", brinquedo.getCategoriaMae().getNome());
    }
}
