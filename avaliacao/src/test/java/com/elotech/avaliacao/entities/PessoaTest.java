package com.elotech.avaliacao.entities;

import com.elotech.avaliacao.exception.BeanValidator;
import com.elotech.avaliacao.exception.ValidacaoDeDadosException;
import com.elotech.avaliacao.utils.PessoaTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class PessoaTest {

    private final BeanValidator<Pessoa> validator = new BeanValidator<>();

    @Autowired
    private PessoaTestUtil pessoaTestUtil;

    @Test
    public void testDummy() {
        assertThat(pessoaTestUtil.criaPessoaSemPersistencia()).isNotNull();
    }

    @Test
    public void testValidaSemNome() {
        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setNome(null);
            validator.validar(pessoa);
        }).getMessage()).isEqualTo("O nome não pode ser vazio");
    }

    @Test
    public void testValidaSemCPF() {
        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setCpf(null);
            validator.validar(pessoa);
        }).getMessage()).isEqualTo("O CPF não pode ser vazio");
    }

    @Test
    public void testValidaCPFInvalido() {
        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setCpf("12345678912");
            validator.validar(pessoa);
        }).getMessage()).isEqualTo("CPF informado é inválido");
    }

    @Test
    public void testValidaSemDataDeNascimento() {
        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setDataDeNascimento(null);
            validator.validar(pessoa);
        }).getMessage()).isEqualTo("A data de nascimento não pode ser vazio");
    }

    @Test
    public void testValidaDataDeNascimentoFutura() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setDataDeNascimento(dateFormat.parse("01/01/2025"));
            validator.validar(pessoa);
        }).getMessage()).isEqualTo("Data não pode ser maior que a data de hoje");
    }

    @Test
    public void testValidaSemContato() {
        assertThat(assertThrows(ValidacaoDeDadosException.class, () -> {
            Pessoa pessoa = pessoaTestUtil.criaPessoaSemPersistencia();
            pessoa.setContato(null);
            validator.validar(pessoa);
        }).getMessage()).isEqualTo("É necessário selecionar pelo menos um Contato");
    }

}
