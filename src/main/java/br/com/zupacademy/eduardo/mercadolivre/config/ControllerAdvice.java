package br.com.zupacademy.eduardo.mercadolivre.config;

import br.com.zupacademy.eduardo.mercadolivre.config.dto.ErroDeFormularioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @Autowired
    private MessageSource source;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException exception) {
        List<ErroDeFormularioDTO> errosDto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        //Validações nível de atributo
        fieldErrors.forEach(e -> {
            String mensagem = source.getMessage(e, LocaleContextHolder.getLocale());
            ErroDeFormularioDTO erro = new ErroDeFormularioDTO(e.getField(), mensagem);

            errosDto.add(erro);
        });

        // Validações nivel de Classe
        exception.getBindingResult().getGlobalErrors().forEach(e -> {
            errosDto.add(new ErroDeFormularioDTO(e.getObjectName(), e.getDefaultMessage()));
        });

        return errosDto;
    }
}