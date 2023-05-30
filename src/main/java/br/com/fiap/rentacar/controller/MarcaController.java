package br.com.fiap.rentacar.controller;

import br.com.fiap.rentacar.entity.Marca;
import br.com.fiap.rentacar.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marcas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MarcaController {

    @Autowired
    private MarcaRepository repository;

    @GetMapping
    public List<Marca> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    //public ResponseEntity<?> findById(@PathVariable Long id) {
    public ResponseEntity<Marca> findById(@PathVariable Long id) {
        Optional<Marca> result = repository.findById(id);
        if (result.isEmpty()) {
            //String message = "Marca " + id + " não encontrada";
            //Map<String, String> response = new HashMap<>();
           //response.put("message", "Marca " + id + " não encontrada");
            //ErrorResponse errorResponse = new ErrorResponse("Marca " + id + " não encontrada");
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result.get());
    }

    @PostMapping
    public ResponseEntity<Marca> insert(@RequestBody Marca marca) {
        var exists = repository.findByNomeIgnoreCase(marca.getNome());
        if (exists.isEmpty()) {
            Marca saved = repository.save(marca);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> update(@PathVariable Long id,
                                        @RequestBody Marca marca) {
        var exists = repository.findByNomeIgnoreCaseAndIdNot(marca.getNome(), id);
        if (!exists.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        //var result = repository.findById(id);
        //if (result.isEmpty()) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var updated = repository.save(marca);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Marca> delete(@PathVariable Long id) {
        var result = repository.findById(id);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var exists = repository.findModelos(id);
        if (!exists.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
