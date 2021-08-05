package br.com.zupacademy.eduardo.mercadolivre.controller;

import br.com.zupacademy.eduardo.mercadolivre.clients.AcaoAposPagamentoClient;
import br.com.zupacademy.eduardo.mercadolivre.component.CentralEmail;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.CaracteristicaRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.PagamentoPaypalRequest;
import br.com.zupacademy.eduardo.mercadolivre.controller.request.pagamento.StatusPagamento;
import br.com.zupacademy.eduardo.mercadolivre.infra.ExecuteTransaction;
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
import java.util.Set;

public class PagamentoControllerTest {

    private PagamentoController pagamentoController;
    private EntityManager manager = Mockito.mock(EntityManager.class);
    private ExecuteTransaction executor = Mockito.mock(ExecuteTransaction.class);
    private AcaoAposPagamentoClient client = Mockito.mock(AcaoAposPagamentoClient.class);
    private CentralEmail centralEmail = new CentralEmail();
    private PagamentoPaypalRequest paypalRequest;
    private PagamentoPaypalRequest paypalRequestErro;
    private Compra compraReturn;
    private Produto produto;
    private ArgumentCaptor<Pagamento> argumentCaptor;

    @BeforeEach
    void setUp() throws Exception {
        Set<CaracteristicaRequest> caracteristicas = Set.of(new CaracteristicaRequest("Cor", "Branco"));
        produto = new Produto("Barbeador", new BigDecimal("200.0"), 100L, "Barbeador profissional",
                            new Categoria("Uteis"), new Usuario("teste@zup.com", new Password("123456")), caracteristicas);
        paypalRequest = new PagamentoPaypalRequest("123456789", StatusPagamento.SUCESSO);
        paypalRequestErro = new PagamentoPaypalRequest("123456789", StatusPagamento.ERRO);

        compraReturn = new Compra(produto, 2L, new Usuario("test@zup.com", new Password("123456")),
                            new BigDecimal("152.0"),GatewayPagamento.PAYPAL);

        ReflectionTestUtils.setField(compraReturn, "id", 1L);
        Mockito.when(manager.find(Compra.class, 1L)).thenReturn(compraReturn);
        Mockito.doCallRealMethod().when(executor).inTransaction(Mockito.any());

        argumentCaptor = ArgumentCaptor.forClass(Pagamento.class);

        pagamentoController = new PagamentoController(manager, executor, client, centralEmail);
    }

    @Test
    @DisplayName("Valida status INICIADO e Pagamento com SUCESSO")
    public void test1() {
        Assertions.assertTrue(compraReturn.statusValido());

        ResponseEntity<?> responseEntity = pagamentoController.pagamentoPaypal(paypalRequest, 1L);

        Mockito.verify(manager, Mockito.atLeastOnce()).persist(paypalRequest.toModel(compraReturn));
        Mockito.verify(manager).persist(argumentCaptor.capture());
        Pagamento pagamento = argumentCaptor.getValue();

        Assertions.assertEquals(StatusPagamento.SUCESSO, pagamento.getStatus());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Valida status INICIADO e Pagamento com ERRO")
    public void test2() {
        Assertions.assertTrue(compraReturn.statusValido());

        ResponseEntity<?> responseEntity = pagamentoController.pagamentoPaypal(paypalRequestErro, 1L);

        Mockito.verify(manager).persist(argumentCaptor.capture());
        Pagamento pagamento = argumentCaptor.getValue();

        Assertions.assertEquals(StatusPagamento.ERRO, pagamento.getStatus());
    }

    @Test
    @DisplayName("Valida status FINALIZADO e nem gera pagamento")
    public void test3() {
        ReflectionTestUtils.setField(compraReturn, "status", StatusCompra.FINALIZADA);

        ResponseEntity<?> responseEntity = pagamentoController.pagamentoPaypal(paypalRequestErro, 1L);

        Assertions.assertFalse(compraReturn.statusValido());
    }
}
