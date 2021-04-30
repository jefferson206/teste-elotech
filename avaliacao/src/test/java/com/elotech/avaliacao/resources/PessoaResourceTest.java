package com.elotech.avaliacao.resources;

import com.elotech.avaliacao.entities.Pessoa;
import com.elotech.avaliacao.services.PessoaService;
import com.elotech.avaliacao.utils.PessoaTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PessoaResourceTest {

    private final String API_ENDPOINT = "/pessoas/";

    @MockBean
    private PessoaService service;

    @Autowired
    private PessoaTestUtil pessoaTestUtil;

    @Autowired
    private MockMvc mockMvc;

    private Pessoa pessoa;

    @BeforeEach
    public void setup() {
        pessoa = pessoaTestUtil.criaPessoaSemPersistencia();

        Page<Pessoa> pagedResponse = new PageImpl(Arrays.asList(pessoa));

        BDDMockito.given(this.service.salvar(Mockito.any(Pessoa.class))).willReturn(pessoa);
        BDDMockito.given(this.service.buscarTodosPaginado(Mockito.any(Pageable.class))).willReturn(pagedResponse);
        BDDMockito.given(this.service.buscarPorId(Mockito.anyLong())).willReturn(pessoa);
        BDDMockito.doNothing().when(service).remover(Mockito.anyLong());

    }

    @Test
    @SneakyThrows
    public void testCriar() {
        mockMvc.perform(MockMvcRequestBuilders.post(API_ENDPOINT)
            .content(converterParaJson((pessoa)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    public void testListar() {
        service.salvar(pessoa);

        mockMvc.perform(MockMvcRequestBuilders.get(API_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    @SneakyThrows
    public void testPesquisarPorId() {
        service.salvar(pessoa);
        pessoa.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.get(API_ENDPOINT + pessoa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Elotech"));
    }

    @Test
    @SneakyThrows
    public void testAlterar() {
        service.salvar(pessoa);
        pessoa.setId(1L);
        pessoa.setNome("Jefferson");

        mockMvc.perform(MockMvcRequestBuilders.put(API_ENDPOINT + pessoa.getId())
            .content(converterParaJson(pessoa))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value( "Jefferson"));

    }

    @Test
    @SneakyThrows
    public void testRemover() {
        service.salvar(pessoa);
        pessoa.setId(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(API_ENDPOINT + pessoa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.mensagem").value("Pessoa removida com sucesso"));
    }

    @SneakyThrows
    private String converterParaJson(Pessoa entidade) {
        return new ObjectMapper().writeValueAsString(entidade);
    }

}
