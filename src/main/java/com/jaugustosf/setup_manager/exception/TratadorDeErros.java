package com.jaugustosf.setup_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// 1. Avisa o Spring: "Fique escutando os erros de todos os Controllers!"
@RestControllerAdvice
public class TratadorDeErros {

    // 2. Avisa: "Se o erro for de Validação (MethodArgumentNotValidException), mande para este método!"
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Mantemos o 400, pois a culpa ainda é do usuário
    public Map<String, String> lidarComErrosDeValidacao(MethodArgumentNotValidException ex) {

        // Criamos um "dicionário" (Map) vazio. O Jackson vai transformar isso num JSON bonitinho.
        Map<String, String> errosFormatados = new HashMap<>();

        // 3. Entramos na exceção bruta do Java, pegamos a lista de erros e fazemos um loop:
        ex.getBindingResult().getAllErrors().forEach((erro) -> {

            // Descobrimos o nome do atributo que deu erro (ex: "nome" ou "categoria")
            String nomeDoCampo = ((FieldError) erro).getField();

            // Pegamos a nossa mensagem customizada ("O nome do equipamento não pode estar vazio!")
            String mensagem = erro.getDefaultMessage();

            // Colocamos no dicionário: "nome": "O nome... vazio!"
            errosFormatados.put(nomeDoCampo, mensagem);
        });

        // 4. Devolvemos o nosso dicionário limpo e direto ao ponto!
        return errosFormatados;
    }

    @ExceptionHandler(RegistoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 2. Força o retorno do Status 404 (Not Found)!
    public Map<String, String> lidarComRegistoNaoEncontrado(RegistoNaoEncontradoException ex) {

        // Criamos o nosso dicionário (Map) para gerar o JSON
        Map<String, String> erroFormatado = new HashMap<>();

        // Colocamos a chave "mensagem" e extraímos o texto que escrevemos lá no Service
        erroFormatado.put("mensagem", ex.getMessage());

        return erroFormatado;
    }
}