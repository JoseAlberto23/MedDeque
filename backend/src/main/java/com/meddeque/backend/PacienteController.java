package com.meddeque.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @GetMapping
    public List<Paciente> listar() {
        return repository.findAllByOrderByGravidadeAsc();
    }

    @PostMapping
    public Paciente cadastrar(@RequestBody Paciente paciente) {
        return repository.save(paciente);
    }
}
