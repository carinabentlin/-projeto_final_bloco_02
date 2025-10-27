package com.generation.farmaciaon.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmaciaon.model.Produto;
import com.generation.farmaciaon.repository.CategoriaRepository;
import com.generation.farmaciaon.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // GET all
    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    // GET by Id
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET by Nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
    }

    // GET by Preço (faixa)
    @GetMapping("/preco")
    public ResponseEntity<List<Produto>> getByPrecoBetween(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return ResponseEntity.ok(produtoRepository.findAllByPrecoBetween(min, max));
    }

    // POST
    @PostMapping
    public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria é obrigatória");
        }

        if (!categoriaRepository.existsById(produto.getCategoria().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }

    // PUT
    @PutMapping
    public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
        Optional<Produto> existente = produtoRepository.findById(produto.getId());

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (produto.getCategoria() == null || produto.getCategoria().getId() == null ||
                !categoriaRepository.existsById(produto.getCategoria().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria inválida");
        }

        return ResponseEntity.ok(produtoRepository.save(produto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        produtoRepository.deleteById(id);
    }

    // Produtos com preço maior que X (ordem crescente)
    @GetMapping("/preco/maior/{valor}")
    public ResponseEntity<List<Produto>> getByPrecoMaior(@PathVariable BigDecimal valor) {
        return ResponseEntity.ok(produtoRepository.findAllByPrecoGreaterThanOrderByPrecoAsc(valor));
    }

    // Produtos com preço menor que X (ordem decrescente)
    @GetMapping("/preco/menor/{valor}")
    public ResponseEntity<List<Produto>> getByPrecoMenor(@PathVariable BigDecimal valor) {
        return ResponseEntity.ok(produtoRepository.findAllByPrecoLessThanOrderByPrecoDesc(valor));
    }
}
