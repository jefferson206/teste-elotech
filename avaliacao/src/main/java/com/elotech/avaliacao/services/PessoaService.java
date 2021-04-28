package com.elotech.avaliacao.services;

import com.elotech.avaliacao.entities.Pessoa;
import com.elotech.avaliacao.exception.EntidadeEmUso;
import com.elotech.avaliacao.repositories.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public List<Pessoa> buscarTodos() {
        return this.repository.findAll();
    }

    public Page<Pessoa> buscarTodosPaginado(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public Pessoa buscarPorId(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public Pessoa salvar(Pessoa pessoa) {
        try {
            return this.repository.save(pessoa);
        } catch (DataIntegrityViolationException e) {
            throw e;
        }
    }

    public void remover(Long id) {
        try {
            this.repository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new EntidadeEmUso("A entidade não pode ser removida");
        }
    }

}
