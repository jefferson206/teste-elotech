package com.elotech.avaliacao.exception;

public class EntidadeEmUso extends RuntimeException {

    public EntidadeEmUso(String mensagem) {
        super(mensagem);
    }
}
