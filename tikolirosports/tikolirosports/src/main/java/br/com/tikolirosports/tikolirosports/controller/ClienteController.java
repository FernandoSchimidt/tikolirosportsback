package br.com.tikolirosports.tikolirosports.controller;

import br.com.tikolirosports.tikolirosports.model.Cliente;
import br.com.tikolirosports.tikolirosports.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> listar(){
        return  clienteRepository.findAll();
    }
    @GetMapping("/{id}")
    public Optional<Cliente> getById(@PathVariable Long id){
        return  clienteRepository.findById(id);
    }
    @PostMapping
    public Cliente salvar(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        clienteRepository.deleteById(id);
    }
}
