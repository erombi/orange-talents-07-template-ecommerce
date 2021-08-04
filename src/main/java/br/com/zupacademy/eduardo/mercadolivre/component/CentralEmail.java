package br.com.zupacademy.eduardo.mercadolivre.component;

import br.com.zupacademy.eduardo.mercadolivre.model.Compra;
import br.com.zupacademy.eduardo.mercadolivre.model.Pergunta;
import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CentralEmail {

    public void enviaPerguntaAoVendedor(Pergunta pergunta) {
        Usuario vendedor = pergunta.getVendedor();
        System.out.println("Olá " + vendedor.getUsername() + ", existem novas perguntas sobre o produto " + pergunta.getNomeProduto() +". Por favor, verifique !");
    }

    public void enviaNovaCompra(Compra compra) {
        Usuario vendedor = compra.getVendedor();
        System.out.println("Olá " + vendedor.getUsername() + ", foi iniciada a compra de produto seu !");
    }

    public void enviaConfirmacaoPagamento(Compra compra) {
        Usuario vendedor = compra.getComprador();
        System.out.println("Olá " + vendedor.getUsername() + ", foi confirmado o seu pagamento da compra " + compra.getId() + " !");
    }

    public void enviaErroPagamento(Compra compra) {
        Usuario vendedor = compra.getComprador();
        System.out.println("Olá " + vendedor.getUsername() + ", ocorreu um erro no pagamento da compra " + compra.getId() + ", por favor tente novamente !\n" +
                "Link: " + compra.getUrlRedirecionamento(compra.getId()));
    }
}
