package br.com.tikolirosports.tikolirosports.controller;

import br.com.tikolirosports.tikolirosports.model.Time;
import br.com.tikolirosports.tikolirosports.repository.TimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/times")
@RequiredArgsConstructor
public class TimeController {
    private final TimeRepository timeRepository;

    @GetMapping
    public List<Time> listar() {
        return timeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Time> getById(@PathVariable Long id) {
        return timeRepository.findById(id);
    }

    @PostMapping
    public Time salvar(@RequestBody Time time) {
        return timeRepository.save(time);
    }

    @PutMapping("/{id}")
    public Time atualizar(@PathVariable Long id, @RequestBody Time time) {
        time.setId(id);
        return timeRepository.save(time);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        timeRepository.deleteById(id);
    }
}
