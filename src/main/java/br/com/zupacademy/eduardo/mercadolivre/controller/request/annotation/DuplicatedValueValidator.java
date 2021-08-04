package br.com.zupacademy.eduardo.mercadolivre.controller.request.annotation;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class DuplicatedValueValidator implements ConstraintValidator<DuplicatedValue, Object> {

    private String field;
    private Class<?> clazz;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(DuplicatedValue constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.clazz = constraintAnnotation.clazz();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Query query = entityManager.createQuery("SELECT 1 from " + clazz.getName() + " WHERE " + field + " = :value");
        query.setParameter("value", value);
        List<?> result = query.getResultList();

        Assert.state(result.size() <= 1, "Foi encontrado um(a) ou mais " + clazz + "(s) com o " + field + " igual a " + value);

        return result.isEmpty();
    }
}
