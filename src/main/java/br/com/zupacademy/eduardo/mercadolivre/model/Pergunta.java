package br.com.zupacademy.eduardo.mercadolivre.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_produto_pergunta")
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    private Instant criadoEm = Instant.now();

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @ManyToOne
    private Produto produto;

    @Deprecated
    public Pergunta() {    }

    public Pergunta(String titulo, Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
    }

    public String getTitulo() {
        return titulo;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public Usuario getVendedor() {
        return this.produto.getUsuario();
    }

    public String getNomeProduto() {
        return this.produto.getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pergunta pergunta = (Pergunta) o;
        return Objects.equals(id, pergunta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
