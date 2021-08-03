package br.com.zupacademy.eduardo.mercadolivre.model;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.CaracteristicaRequest;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
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

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @OneToMany(mappedBy = "produto")
    private Set<Opniao> opnioes = new HashSet<>();

    @OneToMany(mappedBy = "produto")
    private Set<Pergunta> perguntas = new HashSet<>();

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

    public Usuario getUsuario() {
        return usuario;
    }

    public String getVendedor() { return "Jeovaldo"; }

    public BigDecimal getValor() {
        return valor;
    }

    public Long getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return Collections.unmodifiableSet(caracteristicas);
    }

    public Set<ImagemProduto> getImagens() {
        return Collections.unmodifiableSet(imagens);
    }

    public String montaStringCategorias() {
        return categoria.montaStringCategorias();
    }

    public String getNome() {
        return nome;
    }

    public Set<Opniao> getOpnioes() {
        return opnioes;
    }

    public Set<Pergunta> getPerguntas() {
        return perguntas;
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

    public void associaImagens(Set<String> links) {
        Set<ImagemProduto> imagemProdutoSet = links.stream().map(link -> new ImagemProduto(this, link)).collect(Collectors.toSet());
        this.imagens.addAll(imagemProdutoSet);
    }

    public boolean pertenceAoDono(Usuario usuario) {
        return this.usuario.equals(usuario);
    }

    public Double calculaOpniaoMedia() {
        OptionalDouble average = opnioes.stream().map(Opniao::getNota).mapToDouble(Integer::doubleValue).average();

        if (average.isPresent()) {
            return average.getAsDouble();
        }

        return 0.0;
    }

    public boolean abateEstoque(Integer quantidade) {
        if (quantidade > this.quantidadeDisponivel) return false;

        this.quantidadeDisponivel -= quantidade;
        return true;
    }
}
