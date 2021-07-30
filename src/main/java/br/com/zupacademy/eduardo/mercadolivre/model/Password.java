package br.com.zupacademy.eduardo.mercadolivre.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;

public class Password {

    private final Logger logger = LoggerFactory.getLogger(Password.class);
    private final String password;
    private final BCryptPasswordEncoder encoder;

    public Password(@NotBlank String password) {
        Assert.state(password != null, "Password não pode ser nulo !");
        Assert.state(!password.isBlank(), "Password não pode ser nulo ou vazio !");
        Assert.state(password.length() >= 6, "Password precisa ter no mínimo 6 caracteres !");

        encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public String get() {
        return password;
    }

    public BCryptPasswordEncoder getEncoder() {
        return this.encoder;
    }
}
