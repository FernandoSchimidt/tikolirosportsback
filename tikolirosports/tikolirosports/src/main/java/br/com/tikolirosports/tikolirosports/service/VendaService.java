package br.com.tikolirosports.tikolirosports.service;

import br.com.tikolirosports.tikolirosports.model.ItemVenda;
import br.com.tikolirosports.tikolirosports.model.MovimentoEstoque;
import br.com.tikolirosports.tikolirosports.model.Produto;
import br.com.tikolirosports.tikolirosports.model.Venda;
import br.com.tikolirosports.tikolirosports.repository.MovimentoEstoqueRepository;
import br.com.tikolirosports.tikolirosports.repository.ProdutoRepository;
import br.com.tikolirosports.tikolirosports.repository.VendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentoEstoqueRepository movimentoEstoqueRepository;

    @Transactional
    public Venda registrarVenda(Venda venda) {
        double total = 0.0;

        for (ItemVenda item : venda.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + item.getProduto().getId()));

            // Calcula subtotal e atualiza estoque
            double subtotal = item.getQuantidade() * item.getPrecoUnitario();
            total += subtotal;

            produto.setEstoqueAtual(produto.getEstoqueAtual() - item.getQuantidade());
            produtoRepository.save(produto);

            // Registra movimento de saída
            MovimentoEstoque mov = MovimentoEstoque.builder()
                    .tipo("SAIDA")
                    .quantidade(item.getQuantidade())
                    .descricao("Venda ID: temporária")
                    .dataMovimento(LocalDateTime.now())
                    .produto(produto)
                    .build();
            movimentoEstoqueRepository.save(mov);

            // Vincula item à venda
            item.setVenda(venda);
        }

        venda.setDataVenda(LocalDateTime.now());
        venda.setValorTotal(total);

        return vendaRepository.save(venda);
    }

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }
    public Optional<Venda> getVendaById(Long id){
        return vendaRepository.findById(id);
    }
}
