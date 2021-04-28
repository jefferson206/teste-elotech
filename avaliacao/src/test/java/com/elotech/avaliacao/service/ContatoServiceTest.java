package com.elotech.avaliacao.service;

import com.elotech.avaliacao.entities.Contato;
import com.elotech.avaliacao.services.ContatoService;
import com.elotech.avaliacao.utils.ContatoTestUtil;
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
public class ContatoServiceTest {

    @Autowired
    private ContatoTestUtil contatoTestUtil;

    @Autowired
    private ContatoService service;

    @Test
    public void testSalvaSucesso() {
        Contato contato = contatoTestUtil.criaContatoSemPersistencia();
        service.salvar(contato);
        assertThat(contato).isNotNull();
        assertThat(contato.getId()).isNotNull();
    }

    @Test
    public void testFalhaSemNomeAoSalvar() {
        assertThrows(ConstraintViolationException.class, () -> {
            Contato contato = contatoTestUtil.criaContatoSemPersistencia();
            contato.setNome(null);
            service.salvar(contato);
        });
    }

    @Test
    public void testFalhaSemTelefoneAoSalvar() {
        assertThrows(ConstraintViolationException.class, () -> {
            Contato contato = contatoTestUtil.criaContatoSemPersistencia();
            contato.setTelefone("");
            service.salvar(contato);
        });
    }

    @Test
    public void testFalhaSemEmailAoSalvar() {
        assertThrows(ConstraintViolationException.class, () -> {
            Contato contato = contatoTestUtil.criaContatoSemPersistencia();
            contato.setEmail(null);
            service.salvar(contato);
        });
    }

    @Test
    public void testFalhaEmailInvalidoAoSalvar() {
        assertThrows(ConstraintViolationException.class, () -> {
            Contato contato = contatoTestUtil.criaContatoSemPersistencia();
            contato.setEmail("teste123");
            service.salvar(contato);
        });
    }
}
