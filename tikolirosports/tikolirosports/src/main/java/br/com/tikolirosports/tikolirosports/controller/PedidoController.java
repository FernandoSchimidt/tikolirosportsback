package br.com.tikolirosports.tikolirosports.controller;

import br.com.tikolirosports.tikolirosports.model.PagamentoPedido;
import br.com.tikolirosports.tikolirosports.model.Pedido;
import br.com.tikolirosports.tikolirosports.repository.PedidoRepository;
import br.com.tikolirosports.tikolirosports.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    @PostMapping
    public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
        Pedido salvo = pedidoService.criarPedido(pedido);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/pagamentos")
    public ResponseEntity<PagamentoPedido> pagar(@PathVariable Long id, @RequestBody PagamentoPedido pagamento) {
        PagamentoPedido p = pedidoService.registrarPagamento(id, pagamento);
        return ResponseEntity.ok(p);
    }

    @PostMapping("/{id}/entregar")
    public ResponseEntity<Pedido> entregar(@PathVariable Long id) {
        Pedido p = pedidoService.entregarPedido(id);
        return ResponseEntity.ok(p);
    }


}