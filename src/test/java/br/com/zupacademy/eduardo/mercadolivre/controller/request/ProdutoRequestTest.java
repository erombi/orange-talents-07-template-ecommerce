package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ProdutoRequestTest {

    @Test
    @DisplayName("Deve retornar lista com características repetidas")
    public void test1() {
        ProdutoRequest produtoRequest = new ProdutoRequest("Caminhão", new BigDecimal("250.0"),
                210L, "Caminhão TruckDay", 1L,
                List.of(new CaracteristicaRequest("Teste", "testes"), new CaracteristicaRequest("Teste", "testes")));

        Set<String> strings = produtoRequest.caracteristicasRepetidas();

        Assertions.assertFalse(strings.isEmpty());
        Assertions.assertEquals(1, strings.size());
        Assertions.assertTrue(strings.contains("nome -> Teste") );

    }
}
