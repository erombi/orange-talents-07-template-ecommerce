package br.com.zupacademy.eduardo.mercadolivre.model;

public enum GatewayPagamento {

    PAYPAL("https://paypal.com?buyerId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}"),
    PAGSEGURO("https://pagseguro.com?returnId={idGeradoDaCompra}&redirectUrl={urlRetornoAppPosPagamento}");

    private String urlPadrao;

    GatewayPagamento(String urlPadrao) {
        this.urlPadrao = urlPadrao;
    }

    public String getUrlRedirecionamento(Long id, String urlConfirmacao) {
        String urlFinal = urlPadrao;
        urlFinal = urlFinal.replace("{idGeradoDaCompra}", id.toString());
        urlFinal = urlFinal.replace("{urlRetornoAppPosPagamento}", urlConfirmacao);

        return urlFinal;
    }
}
