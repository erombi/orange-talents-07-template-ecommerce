package br.com.zupacademy.eduardo.mercadolivre.controller.request.validator;

import br.com.zupacademy.eduardo.mercadolivre.controller.request.ProdutoRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class QuantidadeMinimaCaracteristicasValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ProdutoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        Set<String> repetidos = ((ProdutoRequest) o).caracteristicasRepetidas();

        if (!repetidos.isEmpty()) {
            errors.rejectValue("caracteristicas", null, "Característica repetidas ! " + repetidos.toString());
        }
    }
}
