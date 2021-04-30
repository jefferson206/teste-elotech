package com.elotech.avaliacao.utils;

import com.elotech.avaliacao.entities.Contato;
import com.elotech.avaliacao.entities.Pessoa;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PessoaTestUtil {

    @Autowired
    private ContatoTestUtil contatoTestUtil;

    @SneakyThrows
    public Pessoa criaPessoaSemPersistencia() {
        Contato contato = contatoTestUtil.criaContatoSemPersistencia();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNascimento = dateFormat.parse("04/04/2000");
        contato.setId(1L);
        return Pessoa.builder()
                .nome("Elotech")
                .cpf("13605419003")
                .dataDeNascimento(dataNascimento)
                .contato(Stream.of(contato).collect(Collectors.toSet()))
                .build();
    }

}
