package br.com.zupacademy.eduardo.mercadolivre.infra;

import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.security.MessageDigest;

public class PasswordEncoder {

    public static String encode(@NotBlank String senha) throws Exception {
        Assert.state(!senha.isBlank(), "Password não pode ser nula ou vazia");
        Assert.state(senha.length() >= 6, "Password não pode ser nula ou vazia");

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte hash[] = algorithm.digest(senha.getBytes("UTF-8"));

        StringBuilder texto = new StringBuilder();
        for (byte b : hash) {
            texto.append(String.format("%02X", 0xFF & b));
        }
        return texto.toString();
    }
}
