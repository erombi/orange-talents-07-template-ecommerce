package br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistsIdValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsId {
    //Spring não retorna mensagem que esta no resources -> messages.properties
    //String message() default "{br.com.zupacademy.eduardo.casadocodigo.beanvalidation.existsId}";

    String message() default "Entidade não encontrada !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field() default "";

    Class<?> clazz() ;
}

