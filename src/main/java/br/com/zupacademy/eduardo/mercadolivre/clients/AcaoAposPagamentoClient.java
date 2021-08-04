package br.com.zupacademy.eduardo.mercadolivre.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Map;


@FeignClient(name = "mercadolivre", url = "http://localhost:8080")
public interface AcaoAposPagamentoClient {

    @PostMapping("/notasfiscais")
    void cadastraNotaFiscal(@RequestBody @Valid Map<String, String> request);

    @PostMapping("/rankingvendedores")
    void rankingVendedores(@RequestBody @Valid Map<String, String> request);
}
