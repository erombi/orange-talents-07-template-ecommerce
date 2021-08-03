package br.com.zupacademy.eduardo.mercadolivre.component;

import br.com.zupacademy.eduardo.mercadolivre.model.Pergunta;
import org.springframework.stereotype.Component;

@Component
public class CentralEmail {

    public void enviaPerguntaAoVendedor(Pergunta pergunta) {
        String vendedor = pergunta.getProduto().getVendedor();
        System.out.println("Olá " + vendedor + ", existem novas perguntas sobre o produto " + pergunta.getProduto().getNome() +". Por favor, verifique !");
    }
}
