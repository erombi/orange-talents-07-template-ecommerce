package br.com.zupacademy.eduardo.mercadolivre.controller;

import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.CaracteristicaRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.CompraRequest;
import br.com.zupacademy.eduardo.mercadolivre.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Set;

public class CompraControllerTest {


    private CompraController compraController;
    private EntityManager manager = Mockito.mock(EntityManager.class);
    private CentralEmail centralEmail = new CentralEmail();
    private Produto produto;
    private ArgumentCaptor<Compra> argumentCaptor = ArgumentCaptor.forClass(Compra.class);

    @BeforeEach
    void setUp() throws Exception {
        Set<CaracteristicaRequest> caracteristicas = Set.of(new CaracteristicaRequest("Cor", "Branco"));
        produto = new Produto("Barbeador", new BigDecimal("200.0"), 100L, "Barbeador profissional",
                new Categoria("Uteis"), new Usuario("teste@zup.com", new Password("123456")), caracteristicas);

        Mockito.doAnswer(invocation -> {
            Compra compraSendoSalva = invocation.<Compra>getArgument(0);
            ReflectionTestUtils.setField(compraSendoSalva, "id", 1L);
            return null;
        }).when(manager).persist(Mockito.any(Compra.class));

        compraController = new CompraController(manager, centralEmail);
        Mockito.when(manager.find(Mockito.any(), Mockito.any(Long.class))).thenReturn(produto);
    }

    @Test
    @DisplayName("Deve realizar o cadastro de uma nova Compra")
    public void test1() {
        ResponseEntity<?> responseEntity = compraController.novaCompra(new CompraRequest(GatewayPagamento.PAGSEGURO, 1L, 2L),
                new Usuario("teste@zup.com", new Password("123456")));

        Mockito.verify(manager).persist(argumentCaptor.capture());

        Assertions.assertEquals("https://pagseguro.com?returnId=1&redirectUrl=http://localhost:8080/compras/1/pagamentos/pagseguro",
                                argumentCaptor.getValue().getUrlRedirecionamento());

        Assertions.assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals("https://pagseguro.com?returnId=1&redirectUrl=http://localhost:8080/compras/1/pagamentos/pagseguro",
                    responseEntity.getHeaders().getLocation().toString());
    }

    @Test
    @DisplayName("Deve retornar UnprocessableEntity devido quantidade maior que o estoque")
    public void test2() {
        ResponseEntity<?> responseEntity = compraController.novaCompra(new CompraRequest(GatewayPagamento.PAGSEGURO, 1L, 101L),
                new Usuario("teste@zup.com", new Password("123456")));

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar UnprocessableEntity devido quantidade bem maior que o estoque")
    public void test3() {
        ResponseEntity<?> responseEntity = compraController.novaCompra(new CompraRequest(GatewayPagamento.PAGSEGURO, 1L, 574L),
                new Usuario("teste@zup.com", new Password("123456")));

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar UnprocessableEntity devido quantidade igual a 0")
    public void test4() {
        ResponseEntity<?> responseEntity = compraController.novaCompra(new CompraRequest(GatewayPagamento.PAGSEGURO, 1L, 0L),
                new Usuario("teste@zup.com", new Password("123456")));

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar UnprocessableEntity devido quantidade menor que 0")
    public void test5() {
        ResponseEntity<?> responseEntity = compraController.novaCompra(new CompraRequest(GatewayPagamento.PAGSEGURO, 1L, -115L),
                new Usuario("teste@zup.com", new Password("123456")));

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }
}
