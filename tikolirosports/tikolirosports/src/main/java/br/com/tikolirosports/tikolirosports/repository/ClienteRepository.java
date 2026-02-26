package br.com.tikolirosports.tikolirosports.repository;

import br.com.tikolirosports.tikolirosports.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
