package com.generation.farmaciaon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.farmaciaon.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Consulta todas as categorias que contenham parte do texto no campo "descricao"
    List<Categoria> findAllByDescricaoContainingIgnoreCase(String descricao);
}
