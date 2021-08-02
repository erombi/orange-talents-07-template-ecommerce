package br.com.zupacademy.eduardo.mercadolivre.controller.request;

import br.com.zupacademy.eduardo.mercadolivre.model.Caracteristica;
import br.com.zupacademy.eduardo.mercadolivre.model.Produto;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class CaracteristicaRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CaracteristicaRequest(@NotBlank String nome, @NotBlank String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaracteristicaRequest that = (CaracteristicaRequest) o;
        return Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    public Caracteristica toModel(Produto produto) {
        return new Caracteristica(this.nome, this.descricao, produto);
    }
}
