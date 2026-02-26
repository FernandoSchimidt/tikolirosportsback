package br.com.tikolirosports.tikolirosports.service;

import br.com.tikolirosports.tikolirosports.model.*;
import br.com.tikolirosports.tikolirosports.repository.MovimentoEstoqueRepository;
import br.com.tikolirosports.tikolirosports.repository.PagamentoPedidoRepository;
import br.com.tikolirosports.tikolirosports.repository.PedidoRepository;
import br.com.tikolirosports.tikolirosports.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final MovimentoEstoqueRepository movimentoRepository;
    private final PagamentoPedidoRepository pagamentoRepository;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        double total = 0.0;

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + item.getProduto().getId()));

            double subtotal = item.getQuantidade() * item.getPrecoUnitario();
            total += subtotal;

            // Baixa automática de estoque
            produto.setEstoqueAtual(produto.getEstoqueAtual() - item.getQuantidade());
            produtoRepository.save(produto);

            // Registra movimento no histórico
            MovimentoEstoque movimento = MovimentoEstoque.builder()
                    .tipo("SAIDA")
                    .quantidade(item.getQuantidade())
                    .descricao("Baixa automática do Pedido")
                    .dataMovimento(LocalDateTime.now())
                    .produto(produto)
                    .build();

            movimentoRepository.save(movimento);

            // Vincula item ao pedido
            item.setPedido(pedido);
        }

        pedido.setDataPedido(LocalDateTime.now());
        pedido.setValorTotal(total);
        pedido.setStatus("NOVO");

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public PagamentoPedido registrarPagamento(Long pedidoId, PagamentoPedido pagamento) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pagamento.setPedido(pedido);
        pagamento.setDataPagamento(LocalDateTime.now());
        PagamentoPedido salvo = pagamentoRepository.save(pagamento);

        // atualizar soma de pagamentos no pedido
        double soma = pedido.getPagamentos() == null ? 0.0 :
                pedido.getPagamentos().stream().mapToDouble(PagamentoPedido::getValor).sum();
        soma += pagamento.getValor();
        pedido.setValorPago(soma);

        if (Double.compare(soma, pedido.getValorTotal()) >= 0) {
            pedido.setStatus("PAGO");
        } else {
            pedido.setStatus("PAGO-PARCIAL");
        }
        pedidoRepository.save(pedido);
        return salvo;
    }

    @Transactional
    public Pedido entregarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        // Ao entregar: efetivar baixa de estoque para itens que não foram reservados antes
        for (ItemPedido ip : pedido.getItens()) {
            if (!Boolean.TRUE.equals(ip.getReservado())) {
                Produto prod = produtoRepository.findById(ip.getProduto().getId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
                prod.setEstoqueAtual(prod.getEstoqueAtual() - ip.getQuantidade());
                produtoRepository.save(prod);

                MovimentoEstoque mov = MovimentoEstoque.builder()
                        .tipo("SAIDA")
                        .quantidade(ip.getQuantidade())
                        .descricao("Baixa na entrega do pedido " + pedido.getId())
                        .dataMovimento(LocalDateTime.now())
                        .produto(prod)
                        .build();
                movimentoRepository.save(mov);
            }
        }
        pedido.setStatus("ENTREGUE");
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // demais métodos: listar pedidos, buscar por id, cancelar, ajustar itens, etc.
}