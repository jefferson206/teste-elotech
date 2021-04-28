package com.elotech.avaliacao.resources;


import com.elotech.avaliacao.entities.Pessoa;
import com.elotech.avaliacao.services.PessoaService;
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
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService service;

    @GetMapping
    public List<Pessoa> buscarTodos() {
        return this.service.buscarTodos();
    }

    @GetMapping("/{pessoaId}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long pessoaId) {
        Pessoa pessoa = service.buscarPorId(pessoaId);

        if (pessoa != null ) {
            return ResponseEntity.status(HttpStatus.OK).body(pessoa);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa salvar(@RequestBody Pessoa pessoa) {
        return service.salvar(pessoa);
    }

    @PutMapping("/{pessoaId}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long pessoaId, @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtual = service.buscarPorId(pessoaId);
        if (pessoaAtual != null) {
            BeanUtils.copyProperties(pessoa, pessoaAtual, "id");
            service.salvar(pessoaAtual);
            return ResponseEntity.ok(pessoaAtual);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{pessoaId}")
    public ResponseEntity<Object> deletar(@PathVariable Long pessoaId) {
        Pessoa pessoa = service.buscarPorId(pessoaId);
        if (pessoa != null ) {
            service.remover(pessoa.getId());
            return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("mensagem", "Pessoa removida com sucesso"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensagem", "Pessoa não encontrada!!!"));
    }

}
