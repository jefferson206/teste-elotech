package com.elotech.avaliacao.resources;

import com.elotech.avaliacao.entities.Contato;
import com.elotech.avaliacao.services.ContatoService;
import com.elotech.avaliacao.utils.ContatoTestUtil;
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
public class ContatoResourceTest {

    private final String API_ENDPOINT = "/contatos/";

    @MockBean
    private ContatoService service;

    @Autowired
    private ContatoTestUtil contatoTestUtil;

    @Autowired
    private MockMvc mockMvc;

    private Contato contato;

    @BeforeEach
    public void setup() {
        contato = contatoTestUtil.criaContatoSemPersistencia();

        Page<Contato> pagedResponse = new PageImpl(Arrays.asList(contato));

        BDDMockito.given(this.service.salvar(Mockito.any(Contato.class))).willReturn(contato);
        BDDMockito.given(this.service.buscarTodosPaginado(Mockito.any(Pageable.class))).willReturn(pagedResponse);
        BDDMockito.given(this.service.buscarPorId(Mockito.anyLong())).willReturn(contato);
        BDDMockito.doNothing().when(service).remover(Mockito.anyLong());

    }

    @Test
    @SneakyThrows
    public void testCriar() {
        mockMvc.perform(MockMvcRequestBuilders.post(API_ENDPOINT)
            .content(converterParaJson((contato)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    public void testListar() {
        service.salvar(contato);

        mockMvc.perform(MockMvcRequestBuilders.get(API_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    @SneakyThrows
    public void testPesquisarPorId() {
        service.salvar(contato);
        contato.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.get(API_ENDPOINT + contato.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Jefferson"));
    }

    @Test
    @SneakyThrows
    public void testAlterar() {
        service.salvar(contato);
        contato.setId(1L);
        contato.setNome("Elotech");

        mockMvc.perform(MockMvcRequestBuilders.put(API_ENDPOINT + contato.getId())
            .content(converterParaJson(contato))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value( "Elotech"));

    }

    @Test
    @SneakyThrows
    public void testRemover() {
        service.salvar(contato);
        contato.setId(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(API_ENDPOINT + contato.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @SneakyThrows
    private String converterParaJson(Contato entidade) {
        return new ObjectMapper().writeValueAsString(entidade);
    }

}
