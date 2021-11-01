package com.starwarsapi.controller.exception;

import com.starwarsapi.service.exception.NaoPossuiItemException;
import com.starwarsapi.service.exception.ObjetoNaoEncontradoException;
import com.starwarsapi.service.exception.QuantidadeInsuficienteException;
import com.starwarsapi.service.exception.TraidorException;
import com.starwarsapi.service.exception.ValoresIncompativeisException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<ExceptionPadrao> objetoNaoEncontrado(ObjetoNaoEncontradoException e, HttpServletRequest req) {
        ExceptionPadrao err = new ExceptionPadrao(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(QuantidadeInsuficienteException.class)
    public ResponseEntity<ExceptionPadrao> quantidadeInsuficiente(QuantidadeInsuficienteException e, HttpServletRequest req) {
        ExceptionPadrao err = new ExceptionPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(NaoPossuiItemException.class)
    public ResponseEntity<ExceptionPadrao> naoPossuiItem(NaoPossuiItemException e, HttpServletRequest req) {
        ExceptionPadrao err = new ExceptionPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ValoresIncompativeisException.class)
    public ResponseEntity<ExceptionPadrao> valoresIncompativeis(ValoresIncompativeisException e, HttpServletRequest req) {
        ExceptionPadrao err = new ExceptionPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(TraidorException.class)
    public ResponseEntity<ExceptionPadrao> traidor(TraidorException e, HttpServletRequest req) {
        ExceptionPadrao err = new ExceptionPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionPadrao> methodArgumentNotFoundException(MethodArgumentNotValidException e, HttpServletRequest req) {
        ValidacaoException err = new ValidacaoException(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            err.addErros(new CampoMensagem(fe.getField(), fe.getDefaultMessage()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
