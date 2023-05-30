package br.com.fiap.rentacar.controller;

import br.com.fiap.rentacar.entity.Modelo;
import br.com.fiap.rentacar.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelos")
public class ModeloController {

    @Autowired
    private ModeloRepository repository;

    @GetMapping
    public List<Modelo> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> findById(@PathVariable Long id) {
        var result = repository.findById(id);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

    @PostMapping
    public ResponseEntity<Modelo> insert(@RequestBody Modelo modelo) {
        var exists = repository.findByPotenciaAndNomeIgnoreCase(modelo.getPotencia(), modelo.getNome());
        if (!exists.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var saved = repository.save(modelo);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modelo> update(@PathVariable Long id,
                                         @RequestBody Modelo modelo) {
        var exists = repository.findByPotenciaAndNomeIgnoreCaseAndIdNot(modelo.getPotencia(), modelo.getNome(), id);
        if (!exists.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var result = repository.findById(id);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updated = repository.save(modelo);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Modelo> delete(@PathVariable Long id) {
        var exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
