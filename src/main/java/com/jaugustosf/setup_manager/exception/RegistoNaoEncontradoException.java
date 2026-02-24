package com.jaugustosf.setup_manager.exception;

// Estendemos RuntimeException para não sermos obrigados a colocar 'throws' em todos os métodos
public class RegistoNaoEncontradoException extends RuntimeException {

    // O construtor recebe a nossa mensagem personalizada e passa-a para o "pai" (super)
    public RegistoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}