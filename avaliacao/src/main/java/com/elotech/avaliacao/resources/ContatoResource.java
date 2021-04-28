package com.elotech.avaliacao.resources;


import com.elotech.avaliacao.entities.Contato;
import com.elotech.avaliacao.services.ContatoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/contatos")
public class ContatoResource {

    @Autowired
    private ContatoService service;

    @GetMapping
    public List<Contato> buscarTodos() {
        return this.service.buscarTodos();
    }

    @GetMapping("/{contatoId}")
    public ResponseEntity<Contato> buscarPorId(@PathVariable Long contatoId) {
        Contato contato = service.buscarPorId(contatoId);

        if (contato != null ) {
            return ResponseEntity.status(HttpStatus.OK).body(contato);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contato salvar(@RequestBody Contato contato) {
        return service.salvar(contato);
    }

    @PutMapping("/{contatoId}")
    public ResponseEntity<Contato> atualizar(@PathVariable Long contatoId, @RequestBody Contato contato) {
        Contato contatoAtual = service.buscarPorId(contatoId);
        if (contatoAtual != null) {
            BeanUtils.copyProperties(contato, contatoAtual, "id");
            service.salvar(contatoAtual);
            return ResponseEntity.ok(contatoAtual);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{contatoId}")
    public ResponseEntity<Object> deletar(@PathVariable Long contatoId) {
        Contato contato = service.buscarPorId(contatoId);
        if (contato != null ) {
            service.remover(contato.getId());
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("mensagem", "Contato removido com sucesso"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensagem", "Contato não encontrado!!!"));
    }

}
