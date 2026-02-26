package br.com.tikolirosports.tikolirosports.repository;

import br.com.tikolirosports.tikolirosports.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    long countByCategoriaId(Long categoriaId);
    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
