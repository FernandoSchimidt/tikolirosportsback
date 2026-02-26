package br.com.tikolirosports.tikolirosports.controller;

import br.com.tikolirosports.tikolirosports.model.Venda;
import br.com.tikolirosports.tikolirosports.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService vendaService;

    @PostMapping
    public Venda registrar(@RequestBody Venda venda) {
        return vendaService.registrarVenda(venda);
    }

    @GetMapping
    public List<Venda> listar() {
        return vendaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Venda> getVendaById(@PathVariable Long id) {
        return vendaService.getVendaById(id);
    }


}
