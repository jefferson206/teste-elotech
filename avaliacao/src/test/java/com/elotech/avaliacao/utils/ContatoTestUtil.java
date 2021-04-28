package com.elotech.avaliacao.utils;

import com.elotech.avaliacao.entities.Contato;
import org.springframework.stereotype.Component;

@Component
public class ContatoTestUtil {

    public Contato criaContatoSemPersistencia() {
        return Contato.builder()
                .nome("Jefferson")
                .telefone("4433226633")
                .email("a@a.com.br")
                .build();
    }

}
