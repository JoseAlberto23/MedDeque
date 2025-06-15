package com.meddeque.backend.controller;

import com.meddeque.backend.model.Paciente;
import com.meddeque.backend.service.FilaTriagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/triagem")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500", "http://localhost:5500"})
public class TriagemController {
    
    @Autowired
    private FilaTriagemService filaTriagemService;
    
    /**
     * Cadastra novo paciente na fila de espera
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarPaciente(@RequestBody Paciente paciente) {
        try {
            // Validações básicas
            if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome é obrigatório");
            }
            
            if (paciente.getCpf() == null || paciente.getCpf().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("CPF é obrigatório");
            }
            
            if (paciente.getSintomas() == null || paciente.getSintomas().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Sintomas são obrigatórios");
            }
            
            // Remove formatação do CPF
            paciente.setCpf(paciente.getCpf().replaceAll("[^0-9]", ""));
            
            boolean sucesso = filaTriagemService.adicionarPaciente(paciente);
            
            if (!sucesso) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF já cadastrado");
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }
    
    /**
     * Realiza triagem do próximo paciente
     */
    @PostMapping("/triar/{gravidade}")
    public ResponseEntity<?> realizarTriagem(@PathVariable int gravidade) {
        try {
            if (gravidade < 1 || gravidade > 3) {
                return ResponseEntity.badRequest().body("Gravidade deve ser 1, 2 ou 3");
            }
            
            Paciente paciente = filaTriagemService.realizarTriagem(gravidade);
            
            if (paciente == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(paciente);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }
    
    /**
     * Chama próximo paciente para atendimento
     */
    @PostMapping("/chamar-proximo")
    public ResponseEntity<?> chamarProximoPaciente() {
        try {
            Paciente paciente = filaTriagemService.chamarProximoPaciente();
            
            if (paciente == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(paciente);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }
    
    /**
     * Finaliza atendimento
     */
    @PostMapping("/finalizar/{cpf}")
    public ResponseEntity<?> finalizarAtendimento(@PathVariable String cpf) {
        try {
            cpf = cpf.replaceAll("[^0-9]", "");
            boolean sucesso = filaTriagemService.finalizarAtendimento(cpf);
            
            if (!sucesso) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }
    
    /**
     * Busca paciente por CPF
     */
    @GetMapping("/paciente/{cpf}")
    public ResponseEntity<?> buscarPaciente(@PathVariable String cpf) {
        try {
            cpf = cpf.replaceAll("[^0-9]", "");
            Paciente paciente = filaTriagemService.buscarPorCpf(cpf);
            
            if (paciente == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(paciente);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }
    
    /**
     * Lista fila de espera para triagem
     */
    @GetMapping("/fila-espera")
    public ResponseEntity<List<Paciente>> getFilaEspera() {
        return ResponseEntity.ok(filaTriagemService.getFilaEspera());
    }
    
    /**
     * Lista fila de prioridade (pacientes triados)
     */
    @GetMapping("/fila-prioridade")
    public ResponseEntity<List<Paciente>> getFilaPrioridade() {
        return ResponseEntity.ok(filaTriagemService.getFilaPrioridade());
    }
    
    /**
     * Lista pacientes em atendimento
     */
    @GetMapping("/em-atendimento")
    public ResponseEntity<List<Paciente>> getPacientesAtendimento() {
        return ResponseEntity.ok(filaTriagemService.getPacientesAtendimento());
    }
    
    /**
     * Lista todos os pacientes
     */
    @GetMapping("/todos")
    public ResponseEntity<List<Paciente>> getTodosPacientes() {
        return ResponseEntity.ok(filaTriagemService.getTodosPacientes());
    }
    
    /**
     * Retorna estatísticas do sistema
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> getEstatisticas() {
        return ResponseEntity.ok(filaTriagemService.getEstatisticas());
    }
    
    /**
     * Remove paciente do sistema
     */
    @DeleteMapping("/remover/{cpf}")
    public ResponseEntity<?> removerPaciente(@PathVariable String cpf) {
        try {
            cpf = cpf.replaceAll("[^0-9]", "");
            boolean sucesso = filaTriagemService.removerPaciente(cpf);
            
            if (!sucesso) {
                return ResponseEntity.badRequest().body("Paciente não pode ser removido");
            }
            
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor");
        }
    }
}