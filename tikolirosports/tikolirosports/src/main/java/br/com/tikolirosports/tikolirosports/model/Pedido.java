package br.com.tikolirosports.tikolirosports.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    private LocalDateTime dataPedido;

    private String status; // EX: "NOVO", "PAGO-PARCIAL", "PAGO", "ENTREGUE", "CANCELADO"

    private Double valorTotal = 0.0;

    private Double valorPago = 0.0;

    private String formaPagamento; // PIX, DINHEIRO, CARTAO, PARCIAL, etc.

    private boolean encomenda; // true = sob demanda

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PagamentoPedido> pagamentos;
}