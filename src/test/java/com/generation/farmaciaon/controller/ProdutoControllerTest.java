package com.generation.farmaciaon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.farmaciaon.model.Categoria;
import com.generation.farmaciaon.model.Produto;
import com.generation.farmaciaon.repository.CategoriaRepository;
import com.generation.farmaciaon.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @BeforeEach
    void limparBanco() {
        produtoRepository.deleteAll();
        categoriaRepository.deleteAll();
    }

    

    @Test
    @DisplayName("GET /produtos deve listar produtos cadastrados")
    void deveListarProdutos() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Antibióticos");
        categoriaRepository.save(categoria);

        Produto produto = new Produto();
        produto.setNome("Amoxicilina 500mg");
        produto.setTipo("Cápsula");
        produto.setLaboratorio("Genérico Pharma");
        produto.setPreco(new BigDecimal("20.00"));
        produto.setEstoque(50);
        produto.setDataValidade(LocalDate.of(2027, 6, 30));
        produto.setCategoria(categoria);

        produtoRepository.save(produto);

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Amoxicilina 500mg"))
                .andExpect(jsonPath("$[0].categoria.descricao").value("Antibióticos"));
    }

    @Test
    @DisplayName("GET /produtos/preco deve buscar produtos por faixa de preço")
    void deveBuscarPorPreco() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setDescricao("Cosméticos");
        categoriaRepository.save(categoria);

        Produto produto = new Produto();
        produto.setNome("Protetor Solar FPS 50");
        produto.setTipo("Loção");
        produto.setLaboratorio("Dermacare");
        produto.setPreco(new BigDecimal("45.90"));
        produto.setEstoque(30);
        produto.setDataValidade(LocalDate.of(2026, 5, 20));
        produto.setCategoria(categoria);

        produtoRepository.save(produto);

        mockMvc.perform(get("/produtos/preco")
                        .param("min", "40.00")
                        .param("max", "50.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Protetor Solar FPS 50"))
                .andExpect(jsonPath("$[0].preco").value(45.90));
    }
}
