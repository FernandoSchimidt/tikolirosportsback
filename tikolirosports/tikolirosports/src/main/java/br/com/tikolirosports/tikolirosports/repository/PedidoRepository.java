package br.com.tikolirosports.tikolirosports.repository;

import br.com.tikolirosports.tikolirosports.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByClienteIdAndDataPedidoBetween(
            Long clienteId,
            LocalDateTime inicio,
            LocalDateTime fim
    );
}
