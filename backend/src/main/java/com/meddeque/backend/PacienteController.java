package com.meddeque.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500", "http://localhost:5500"})
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        try {
            List<Paciente> pacientes = repository.findAllByOrderByGravidadeAscDataHoraCadastroAsc();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Paciente> cadastrar(@RequestBody Paciente paciente) {
        try {
            // Validações básicas
            if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            if (paciente.getCpf() == null || paciente.getCpf().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            if (paciente.getGravidade() < 1 || paciente.getGravidade() > 3) {
                return ResponseEntity.badRequest().build();
            }

            // Verificar se CPF já existe
            if (repository.existsByCpf(paciente.getCpf())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            Paciente pacienteSalvo = repository.save(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteSalvo);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Optional<Paciente> paciente = repository.findById(id);
        return paciente.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/gravidade/{gravidade}")
    public ResponseEntity<List<Paciente>> listarPorGravidade(@PathVariable int gravidade) {
        if (gravidade < 1 || gravidade > 3) {
            return ResponseEntity.badRequest().build();
        }
        
        List<Paciente> pacientes = repository.findByGravidadeOrderByDataHoraCadastroAsc(gravidade);
        return ResponseEntity.ok(pacientes);
    }
}