package br.com.zupacademy.eduardo.mercadolivre.model;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.UsuarioRequest;
import br.com.zupacademy.eduardo.mercadolivre.infra.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;

public class Password {

    private final Logger logger = LoggerFactory.getLogger(Password.class);
    private final String password;

    public Password(@NotBlank String password) {
        Assert.state(password != null, "Password não pode ser nulo !");
        Assert.state(!password.isBlank(), "Password não pode ser nulo ou vazio !");
        Assert.state(password.length() >= 6, "Password precisa ter no mínimo 6 caracteres !");

        try {
            this.password = PasswordEncoder.encode(password);

        } catch (Exception e) {
            logger.error("Erro ao criptografar senha !");
            throw new IllegalArgumentException("Erro ao criptografar senha !");
        }
    }

    public String get() {
        return password;
    }
}
