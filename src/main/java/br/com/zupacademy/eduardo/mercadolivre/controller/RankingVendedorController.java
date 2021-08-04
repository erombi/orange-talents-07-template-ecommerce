package br.com.zupacademy.eduardo.mercadolivre.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rankingvendedores")
public class RankingVendedorController {

    @PostMapping
    public void cadastra(@RequestBody Map<String, String > request) {
        System.out.println("BATEU NO ENDPOINT DE RANKING VENDEDOR - " + request);
    }

}
