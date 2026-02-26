package br.com.tikolirosports.tikolirosports.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagamentoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Pedido pedido;

    private LocalDateTime dataPagamento;

    private Double valor;

    private String forma; // PIX, DINHEIRO, CARTAO, TRANSFERENCIA

    private String observacao;
}