package br.com.tikolirosports.tikolirosports.controller;

import br.com.tikolirosports.tikolirosports.model.Produto;
import br.com.tikolirosports.tikolirosports.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoRepository produtoRepository;

    @GetMapping
    public Page<Produto> listar(
            @RequestParam(required = false) String nome,
            Pageable pageable
    ) {
        if (nome != null && !nome.isEmpty()) {
            return produtoRepository.findByNomeContainingIgnoreCase(nome, pageable);
        }
        return produtoRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Produto> getByid(@PathVariable Long id) {
        return produtoRepository.findById(id);
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        produto.setId(id);
        return produtoRepository.save(produto);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Produto> atualizarParcial(
            @PathVariable Long id,
            @RequestBody Map<String, Object> campos
    ) {
        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    campos.forEach((chave, valor) -> {
                        // Usa reflexão para atualizar apenas os campos enviados
                        Field campo = ReflectionUtils.findField(Produto.class, chave);
                        if (campo != null) {
                            campo.setAccessible(true);
                            ReflectionUtils.setField(campo, produtoExistente, valor);
                        }
                    });
                    Produto atualizado = produtoRepository.save(produtoExistente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }

}
