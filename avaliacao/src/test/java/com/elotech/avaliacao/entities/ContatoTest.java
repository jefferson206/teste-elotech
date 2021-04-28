package com.elotech.avaliacao.entities;

import com.elotech.avaliacao.exception.BeanValidator;
import com.elotech.avaliacao.exception.ValidacaoDeDadosException;
import com.elotech.avaliacao.utils.ContatoTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class ContatoTest {

    private final BeanValidator<Contato> validator = new BeanValidator<>();

    @Autowired
    private ContatoTestUtil contatoTestUtil;

    @Test
    public void testDummy() {
        assertThat(contatoTestUtil.criaContatoSemPersistencia()).isNotNull();
    }

    @Test
    public void testValidaSemNome() {
        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Contato contato = contatoTestUtil.criaContatoSemPersistencia();
            contato.setNome(null);
            validator.validar(contato);
        }).getMessage()).isEqualTo("Nome não pode ser vazio");
    }

    @Test
    public void testValidaSemTelefone() {
        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Contato contato = contatoTestUtil.criaContatoSemPersistencia();
            contato.setTelefone("");
            validator.validar(contato);
        }).getMessage()).isEqualTo("Telefone não pode ser vazio");
    }

    @Test
    public void testValidaSemEmail() {
        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Contato contato = contatoTestUtil.criaContatoSemPersistencia();
            contato.setEmail(null);
            validator.validar(contato);
        }).getMessage()).isEqualTo("E-mail não pode ser vazio");
    }

    @Test
    public void testValidaEmailSeValido() {
        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Contato contato = contatoTestUtil.criaContatoSemPersistencia();
            contato.setEmail("emailInvalido");
            validator.validar(contato);
        }).getMessage()).isEqualTo("Email inválido");
    }

}
