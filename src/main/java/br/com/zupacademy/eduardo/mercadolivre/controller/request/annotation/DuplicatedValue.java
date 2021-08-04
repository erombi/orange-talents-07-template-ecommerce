package br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DuplicatedValueValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedValue {
    //Spring não retorna mensagem que esta no resources -> messages.properties
    //String message() default "{br.com.zupacademy.eduardo.casadocodigo.beanvalidation.duplicatedvalue}";

    String message() default "Campo com valor duplicado encontrado !";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field() default "";

    Class<?> clazz() ;
}
