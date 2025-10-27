package com.generation.farmaciaon.util;

import com.generation.farmaciaon.model.Categoria;
import com.generation.farmaciaon.model.Produto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestBuilder {

    // Criar Categoria para testes
    public static Categoria criarCategoria(Long id, String descricao) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setDescricao(descricao); // Ex.: Analgésicos, Antibióticos, Cosméticos
        return categoria;
    }

    // Criar Produto para testes
    public static Produto criarProduto(Long id, String nome, String tipo, String laboratorio,
                                       BigDecimal preco, Integer estoque, String imagem, LocalDate validade,
                                       Categoria categoria) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setTipo(tipo); // Ex.: comprimido, cápsula, xarope
        produto.setLaboratorio(laboratorio);
        produto.setPreco(preco);
        produto.setEstoque(estoque);
        produto.setImagem(imagem);
        produto.setDataValidade(validade);
        produto.setCategoria(categoria);
        return produto;
    }
}
