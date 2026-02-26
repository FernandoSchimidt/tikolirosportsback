package br.com.tikolirosports.tikolirosports.repository;

import br.com.tikolirosports.tikolirosports.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
