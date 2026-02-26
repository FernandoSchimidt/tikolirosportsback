package br.com.tikolirosports.tikolirosports.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;

    private String campeonato;

    // 🔗 Associação com Categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
