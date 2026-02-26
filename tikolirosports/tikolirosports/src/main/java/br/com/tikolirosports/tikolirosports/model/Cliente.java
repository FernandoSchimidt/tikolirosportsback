package br.com.tikolirosports.tikolirosports.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String telefone;
    private String email;
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time timeFavorito;
}
