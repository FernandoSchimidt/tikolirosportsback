package br.com.tikolirosports.tikolirosports.controller;

import br.com.tikolirosports.tikolirosports.model.Pais;
import br.com.tikolirosports.tikolirosports.model.Time;
import br.com.tikolirosports.tikolirosports.repository.PaisRepository;
import br.com.tikolirosports.tikolirosports.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pais")
@RequiredArgsConstructor
public class PaisController {
    private final PaisRepository paisRepository;

    @GetMapping
    public List<Pais> listar() {
        return paisRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Pais> getById(@PathVariable Long id) {
        return paisRepository.findById(id);
    }

    @PostMapping
    public Pais salvar(@RequestBody Pais pais) {
        return paisRepository.save(pais);
    }

    @PutMapping("/{id}")
    public Pais atualizar(@PathVariable Long id, @RequestBody Pais pais) {
        pais.setId(id);
        return paisRepository.save(pais);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        paisRepository.deleteById(id);
    }
}
