package br.com.tikolirosports.tikolirosports.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String esporte; // futebol, basquete, etc.
    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time time;
    private Integer anoLancamento;
    private String tamanho;
    private String modelo;
    private String versao;
    private Double precoCusto;
    private Double precoVenda;
    private Integer estoqueAtual;
    private Integer estoqueMinimo;
    private String cor;
    private String imagemUrl; // opcional

    // 🔗 Associação com Categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
