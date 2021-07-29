package br.com.zupacademy.eduardo.mercadolivre.model;

import br.com.zupacademy.eduardo.mercadolivre.infra.PasswordEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @Test
    @DisplayName("Deve lançar uma IllegalStateException quando password for nula.")
    public void test1() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Password password = new Password(null);
        });
    }

    @Test
    @DisplayName("Deve lançar uma IllegalStateException quando password for vazia.")
    public void test2() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Password password = new Password("");
        });
    }

    @Test
    @DisplayName("Deve lançar uma IllegalStateException quando password contiver espaços em branco.")
    public void test3() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Password password = new Password("              ");
        });
    }

    @Test
    @DisplayName("Deve lançar uma IllegalStateException quando tamanho da senha for meor que 5")
    public void test4() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Password password = new Password("12345");
        });
    }

    @Test
    @DisplayName("Deve Criar um Password corretamente com 6 caracteres")
    public void test5() {
        String encoded = "";
        try {
            encoded = PasswordEncoder.encode("123456");
        } catch (Exception e) {
            System.out.println("Erro no encode");
        }

        Password password = new Password("123456");

        Assertions.assertNotNull(password);
        Assertions.assertEquals(encoded.length(), password.get().length());
        Assertions.assertEquals(encoded, password.get());
    }
}