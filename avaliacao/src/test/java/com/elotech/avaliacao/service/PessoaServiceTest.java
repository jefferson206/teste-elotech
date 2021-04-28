package com.elotech.avaliacao.service;

import com.elotech.avaliacao.entities.Pessoa;
import com.elotech.avaliacao.services.PessoaService;
import com.elotech.avaliacao.utils.PessoaTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PessoaServiceTest {

    @Autowired
    private PessoaTestUtil pessoaTestUtil;

    @Autowired
    private PessoaService service;

    @Test
    public void testSalvaSucesso() {
        Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
        service.salvar(pessoa);
        assertThat(pessoa).isNotNull();
        assertThat(pessoa.getId()).isNotNull();
    }

    @Test
    public void testFalhaSemNomeAoSalvar() {
        assertThrows(ConstraintViolationException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setNome(null);
            service.salvar(pessoa);
        });
    }

    @Test
    public void testFalhaSemCPFAoSalvar() {
        assertThrows(ConstraintViolationException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setCpf("");
            service.salvar(pessoa);
        });
    }

    @Test
    public void testFalhaCPFInvalidoAoSalvar() {
        assertThrows(ConstraintViolationException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setCpf("12345678912");
            service.salvar(pessoa);
        });
    }

    @Test
    public void testFalhaSemContatoAoSalvar() {
        assertThrows(ConstraintViolationException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setContato(null);
            service.salvar(pessoa);
        });
    }
}
