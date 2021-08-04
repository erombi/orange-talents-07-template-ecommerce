package br.com.zupacademy.eduardo.mercadolivre.model;

public enum GatewayPagamento {

    PAYPAL("https://paypal.com?buyerId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}",
           "http://localhost:8080/compras/{idCompra}/pagamentos/paypal"),
    PAGSEGURO("https://pagseguro.com?returnId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}",
            "http://localhost:8080/compras/{idCompra}/pagamentos/pagseguro");

    private String urlPadrao;
    private String urlConfirmacao;

    GatewayPagamento(String urlPadrao, String urlConfirmacao) {
        this.urlPadrao = urlPadrao;
        this.urlConfirmacao = urlConfirmacao;
    }

    public String getUrlRedirecionamento(Long id) {
        String urlFinal = urlPadrao;
        urlFinal = urlFinal.replace("{idGeradoDaCompra}", id.toString());
        urlFinal = urlFinal.replace("{urlRetornoAppPosPagamento}", getUrlConfirmacaoPagamento(id));

        return urlFinal;
    }

    public String getUrlConfirmacaoPagamento(Long id) {
        String urlFinal = urlConfirmacao;
        urlFinal = urlFinal.replace("{idCompra}", id.toString());

        return urlFinal;
    }

}
