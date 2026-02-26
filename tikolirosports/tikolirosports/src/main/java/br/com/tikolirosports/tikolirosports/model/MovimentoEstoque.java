package br.com.tikolirosports.tikolirosports.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentoEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // ENTRADA / SAIDA
    private Integer quantidade;
    private String descricao;
    private LocalDateTime dataMovimento;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
