package br.com.tikolirosports.tikolirosports.repository;

import br.com.tikolirosports.tikolirosports.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
