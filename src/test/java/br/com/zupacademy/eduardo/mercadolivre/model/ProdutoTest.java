package br.com.zupacademy.eduardo.mercadolivre.model;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.CaracteristicaRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

public class ProdutoTest {

    private Produto produto;
    private Produto produtoImutavel;

    @BeforeEach
    void setUp() throws Exception {
        Set<CaracteristicaRequest> caracteristicas = Set.of(new CaracteristicaRequest("Cor", "Branco"));
        produto = new Produto("Barbeador", new BigDecimal("200.0"), 100L, "Barbeador profissional",
                     new Categoria("Uteis"), new Usuario("teste@zup.com", new Password("123456")), caracteristicas);

        produtoImutavel = new Produto("Barbeador", new BigDecimal("200.0"), 100L, "Barbeador profissional",
                new Categoria("Uteis"), new Usuario("teste@zup.com", new Password("123456")), caracteristicas);
    }

    @Test
    @DisplayName("Deve abater a quantidade do estoque.")
    public void test1() {
        Assertions.assertEquals(100L, produto.getQuantidadeDisponivel());

        produto.abateEstoque(100L);

        Assertions.assertEquals(0L, produto.getQuantidadeDisponivel());
    }

    @Test
    @DisplayName("Não deve abater a quantidade do estoque devido quantidade = 0.")
    public void test2() {
        Assertions.assertEquals(100L, produto.getQuantidadeDisponivel());

        produtoImutavel.abateEstoque(0L);

        Assertions.assertEquals(100L, produto.getQuantidadeDisponivel());
    }

    @Test
    @DisplayName("Não deve abater a quantidade do estoque devido quantidade ser maior que o estoque.")
    public void test3() {
        Assertions.assertEquals(100L, produto.getQuantidadeDisponivel());

        produtoImutavel.abateEstoque(101L);

        produtoImutavel.abateEstoque(158L);

        Assertions.assertEquals(100L, produto.getQuantidadeDisponivel());
    }
}
