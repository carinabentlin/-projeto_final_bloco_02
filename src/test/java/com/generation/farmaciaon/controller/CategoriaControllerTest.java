package com.generation.farmaciaon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.farmaciaon.model.Categoria;
import com.generation.farmaciaon.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @BeforeEach
    void limparBanco() {
        categoriaRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /categorias deve criar categoria com sucesso")
    void deveCriarCategoriaComSucesso() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Vitaminas");

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(categoria)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Vitaminas"));
    }

    @Test
    @DisplayName("GET /categorias deve listar categorias cadastradas")
    void deveListarCategorias() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Antibióticos");
        categoriaRepository.save(categoria);

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Antibióticos"));
    }
}
