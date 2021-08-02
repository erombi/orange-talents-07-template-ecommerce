package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation.ExistsId;
import br.com.zupacademy.eduardo.mercadolivre.model.Categoria;
import br.com.zupacademy.eduardo.mercadolivre.model.Produto;
import br.com.zupacademy.eduardo.mercadolivre.model.Usuario;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProdutoRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    private Long quantidadeDisponivel;

    @Length(max = 1000)
    @NotBlank
    private String descricao;

    @NotNull
    @ExistsId(clazz = Categoria.class, field = "id")
    private Long idCategoria;

    @Size(min = 3)
    @NotNull
    private List<CaracteristicaRequest> caracteristicas = new ArrayList<>();

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProdutoRequest(String nome, BigDecimal valor, Long quantidadeDisponivel, String descricao, Long idCategoria, List<CaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
        this.caracteristicas = caracteristicas;
    }

    public Produto toModel(EntityManager manager, Usuario usuario) {
        Assert.state(this.idCategoria != null, "Categoria é obrigatória !");
        Assert.state(this.caracteristicas.size() >= 3, "Obrigatório no mínimo 3 características !");
        Assert.state(usuario != null, "Usuário não pode ser nulo !");

        Categoria categoria = manager.find(Categoria.class, idCategoria);

        return new Produto(this.nome, this.valor, this.quantidadeDisponivel, this.descricao, categoria, usuario, new HashSet<>(caracteristicas));
    }

    public List<CaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<String> caracteristicasRepetidas() {
        Set<String> nomesIguais = new HashSet<>();
        Set<String> valoresRepetidos = new HashSet<>();

        for (CaracteristicaRequest c : caracteristicas) {
            if (!nomesIguais.add(c.getNome())) {
                valoresRepetidos.add("nome -> " + c.getNome());
            }
        }

        return valoresRepetidos;
    }
}
