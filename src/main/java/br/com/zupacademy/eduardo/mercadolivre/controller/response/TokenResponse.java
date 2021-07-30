package br.com.zupacademy.eduardo.mercadolivre.controller.response;

public class TokenResponse {

    private String type;
    private String token;


    public TokenResponse(String type, String token) {
        this.type = type;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
