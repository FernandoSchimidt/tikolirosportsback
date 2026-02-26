package br.com.tikolirosports.tikolirosports.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    private Produto produto;

    private Integer quantidade;

    private Double precoUnitario;

    private Boolean reservado = false; // true se foi reservado/baixa à vista
}