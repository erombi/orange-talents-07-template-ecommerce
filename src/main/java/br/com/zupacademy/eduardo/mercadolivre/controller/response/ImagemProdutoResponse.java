package br.com.zupacademy.eduardo.mercadolivre.controller.response;

import br.com.zupacademy.eduardo.mercadolivre.model.ImagemProduto;

public class ImagemProdutoResponse {

    private String link;

    public ImagemProdutoResponse(ImagemProduto imagemProduto) {
        this.link = imagemProduto.getLink();
    }

    public String getLink() {
        return link;
    }
}
