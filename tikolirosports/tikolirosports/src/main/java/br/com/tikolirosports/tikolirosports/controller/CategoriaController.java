package br.com.tikolirosports.tikolirosports.controller;

import br.com.tikolirosports.tikolirosports.model.Categoria;
import br.com.tikolirosports.tikolirosports.repository.CategoriaRepository;
import br.com.tikolirosports.tikolirosports.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Categoria> getByid(@PathVariable Long id) {
       return  categoriaRepository.findById(id);
    }

    @PostMapping
    public Categoria salvar(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @PutMapping("/{id}")
    public Categoria atualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        long countProdutos = produtoRepository.countByCategoriaId(id);
        if (countProdutos > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Não é possível excluir uma categoria que possui produtos associados."
            );
        }
        categoriaRepository.deleteById(id);
    }

    // endpoint auxiliar pro frontend validar
    @GetMapping("/{id}/possui-itens")
    public boolean verificarSePossuiItens(@PathVariable Long id) {
        return produtoRepository.countByCategoriaId(id) > 0;
    }
}
