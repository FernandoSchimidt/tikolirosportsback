package br.com.tikolirosports.tikolirosports.model;

import br.com.tikolirosports.tikolirosports.Enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private Double valorTotal = 0.0;

    private Double valorPago = 0.0;

    private String formaPagamento;

    private boolean encomenda;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PagamentoPedido> pagamentos;

    @PrePersist
    public void prePersist() {
        this.dataPedido = LocalDateTime.now();
    }
}