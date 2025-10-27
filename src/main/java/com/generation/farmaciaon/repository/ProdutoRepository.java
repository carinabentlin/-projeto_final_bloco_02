package com.generation.farmaciaon.repository;

import com.generation.farmaciaon.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Busca por nome (ignora maiúsculas/minúsculas)
    List<Produto> findAllByNomeContainingIgnoreCase(String nome);

    // Busca todos os produtos com preço entre valores
    List<Produto> findAllByPrecoBetween(BigDecimal min, BigDecimal max);

    // Busca todos os produtos com preço maior que X em ordem crescente
    List<Produto> findAllByPrecoGreaterThanOrderByPrecoAsc(BigDecimal preco);

    // Busca todos os produtos com preço menor que X em ordem decrescente
    List<Produto> findAllByPrecoLessThanOrderByPrecoDesc(BigDecimal preco);
}

