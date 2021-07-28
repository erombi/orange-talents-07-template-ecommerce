package br.com.zupacademy.eduardo.mercadolivre.model;

import br.com.zupacademy.eduardo.mercadolivre.infra.PasswordEncoder;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    private String login;

    @Length(min = 6)
    @NotBlank
    private String senha;

    @NotNull
    private LocalDate dataCadastro = LocalDate.now();

    @Deprecated
    public Usuario() {    }

    public Usuario(@Email @NotBlank String login, @NotNull Password password) {
        Assert.state(!login.isBlank(), "Login não pode ser nulo ou vazio !");
        Assert.state(!password.get().isBlank(), "Password não pode ser nulo ou vazio !");
        Assert.state(password.get().length() >= 6, "Password precisa ter no mínimo 6 caracteres !");

        this.login = login;
        this.senha = password.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
