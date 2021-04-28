package com.elotech.avaliacao.services;

import com.elotech.avaliacao.entities.Contato;
import com.elotech.avaliacao.exception.EntidadeEmUso;
import com.elotech.avaliacao.repositories.ContatoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContatoService  {

    @Autowired
    private ContatoRepository repository;

    public List<Contato> buscarTodos() {
        return this.repository.findAll();
    }

    public Page<Contato> buscarTodosPaginado(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public Contato buscarPorId(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    public Contato salvar(Contato contato) {
        try {
            return this.repository.save(contato);
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
