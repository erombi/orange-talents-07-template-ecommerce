package br.com.zupacademy.eduardo.mercadolivre.model;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.CaracteristicaRequest;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotNull
    @PositiveOrZero
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    private Long quantidadeDisponivel;

    @Length(max = 1000)
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    @NotNull
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @ManyToOne
    private Usuario usuario;

    private Instant dataCadastro = Instant.now();

    @Deprecated
    public Produto() { }

    public Produto(@NotBlank String nome, @NotNull @PositiveOrZero BigDecimal valor, @NotNull @PositiveOrZero Long quantidadeDisponivel,
                       @NotBlank String descricao, @NotNull Categoria categoria, @NotNull Usuario usuario,
                       @NotNull @Size(min = 3) Set<CaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.caracteristicas = caracteristicas.stream().map(c -> c.toModel(this)).collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
