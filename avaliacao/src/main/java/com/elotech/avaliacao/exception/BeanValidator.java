package com.elotech.avaliacao.exception;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidator<T> {

    public void validar(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> validacoes = validator.validate(entity);

        validacoes.forEach(validacao -> {
            throw new ValidacaoDeDadosException(validacao.getMessage());
        });
    }

    public static <T> BeanValidator of(T entity) {
        BeanValidator<T> beanValidator = new BeanValidator<>();
        beanValidator.validar(entity);
        return beanValidator;
    }
}
