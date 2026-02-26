package br.com.tikolirosports.tikolirosports.controller;

import br.com.tikolirosports.tikolirosports.model.MovimentoEstoque;
import br.com.tikolirosports.tikolirosports.model.Produto;
import br.com.tikolirosports.tikolirosports.repository.MovimentoEstoqueRepository;
import br.com.tikolirosports.tikolirosports.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/estoque")
@RequiredArgsConstructor
public class MovimentoEstoqueController {
    private final MovimentoEstoqueRepository movimentoRepository;
    private final ProdutoRepository produtoRepository;

    @GetMapping
    public List<MovimentoEstoque> listar() {
        return movimentoRepository.findAll();
    }

    @PostMapping("/movimentar")
    public MovimentoEstoque movimentar(@RequestBody MovimentoEstoque movimento) {
        Produto produto = produtoRepository.findById(movimento.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if ("ENTRADA".equalsIgnoreCase(movimento.getTipo())) {
            produto.setEstoqueAtual(produto.getEstoqueAtual() + movimento.getQuantidade());
        } else if ("SAIDA".equalsIgnoreCase(movimento.getTipo())) {
            produto.setEstoqueAtual(produto.getEstoqueAtual() - movimento.getQuantidade());
        } else {
            throw new RuntimeException("Tipo de movimentação inválido. Use ENTRADA ou SAIDA.");
        }

        produtoRepository.save(produto);
        movimento.setDataMovimento(LocalDateTime.now());
        return movimentoRepository.save(movimento);
    }

    @GetMapping("/produto/{id}")
    public List<MovimentoEstoque> listarPorProduto(@PathVariable Long id) {
        return movimentoRepository.findAll()
                .stream()
                .filter(m -> m.getProduto().getId().equals(id))
                .toList();
    }

}
