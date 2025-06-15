package com.meddeque.backend.service;

import com.meddeque.backend.model.Paciente;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class FilaTriagemService {
    
    // Hash Table para armazenamento rápido por CPF
    private final Map<String, Paciente> pacientesPorCpf = new ConcurrentHashMap<>();
    
    // Fila de espera para triagem (FIFO)
    private final Queue<Paciente> filaEspera = new ConcurrentLinkedQueue<>();
    
    // Heap de prioridade para pacientes triados (Min-Heap)
    private final PriorityQueue<Paciente> filaPrioridade = new PriorityQueue<>();
    
    // Lista de pacientes em atendimento
    private final List<Paciente> pacientesAtendimento = new ArrayList<>();
    
    // Lista de pacientes finalizados
    private final List<Paciente> pacientesFinalizados = new ArrayList<>();
    
    /**
     * Adiciona paciente na fila de espera para triagem
     */
    public synchronized boolean adicionarPaciente(Paciente paciente) {
        // Verifica se CPF já existe
        if (pacientesPorCpf.containsKey(paciente.getCpf())) {
            return false; // CPF já cadastrado
        }
        
        // Adiciona na hash table
        pacientesPorCpf.put(paciente.getCpf(), paciente);
        
        // Adiciona na fila de espera
        filaEspera.offer(paciente);
        
        return true;
    }
    
    /**
     * Realiza triagem do próximo paciente da fila
     */
    public synchronized Paciente realizarTriagem(int gravidade) {
        Paciente paciente = filaEspera.poll();
        if (paciente == null) {
            return null; // Fila vazia
        }
        
        // Define gravidade e atualiza status
        paciente.setGravidade(gravidade);
        paciente.setStatus("TRIADO");
        
        // Move para heap de prioridade
        filaPrioridade.offer(paciente);
        
        // Atualiza na hash table
        pacientesPorCpf.put(paciente.getCpf(), paciente);
        
        return paciente;
    }
    
    /**
     * Chama próximo paciente para atendimento (maior prioridade)
     */
    public synchronized Paciente chamarProximoPaciente() {
        Paciente paciente = filaPrioridade.poll();
        if (paciente == null) {
            return null; // Fila vazia
        }
        
        // Atualiza status e adiciona na lista de atendimento
        paciente.setStatus("ATENDIDO");
        paciente.setDataHoraAtendimento(java.time.LocalDateTime.now());
        pacientesAtendimento.add(paciente);
        
        // Atualiza na hash table
        pacientesPorCpf.put(paciente.getCpf(), paciente);
        
        return paciente;
    }
    
    /**
     * Finaliza atendimento de um paciente
     */
    public synchronized boolean finalizarAtendimento(String cpf) {
        Paciente paciente = pacientesPorCpf.get(cpf);
        if (paciente == null || !"ATENDIDO".equals(paciente.getStatus())) {
            return false;
        }
        
        // Remove da lista de atendimento
        pacientesAtendimento.removeIf(p -> p.getCpf().equals(cpf));
        
        // Atualiza status e adiciona na lista de finalizados
        paciente.setStatus("FINALIZADO");
        pacientesFinalizados.add(paciente);
        
        // Atualiza na hash table
        pacientesPorCpf.put(cpf, paciente);
        
        return true;
    }
    
    /**
     * Busca paciente por CPF (O(1) - Hash Table)
     */
    public Paciente buscarPorCpf(String cpf) {
        return pacientesPorCpf.get(cpf);
    }
    
    /**
     * Retorna fila de espera para triagem
     */
    public List<Paciente> getFilaEspera() {
        return new ArrayList<>(filaEspera);
    }
    
    /**
     * Retorna fila de prioridade ordenada
     */
    public List<Paciente> getFilaPrioridade() {
        List<Paciente> lista = new ArrayList<>(filaPrioridade);
        Collections.sort(lista);
        return lista;
    }
    
    /**
     * Retorna pacientes em atendimento
     */
    public List<Paciente> getPacientesAtendimento() {
        return new ArrayList<>(pacientesAtendimento);
    }
    
    /**
     * Retorna todos os pacientes
     */
    public List<Paciente> getTodosPacientes() {
        return new ArrayList<>(pacientesPorCpf.values());
    }
    
    /**
     * Retorna estatísticas do sistema
     */
    public Map<String, Object> getEstatisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalPacientes", pacientesPorCpf.size());
        stats.put("aguardandoTriagem", filaEspera.size());
        stats.put("aguardandoAtendimento", filaPrioridade.size());
        stats.put("emAtendimento", pacientesAtendimento.size());
        stats.put("finalizados", pacientesFinalizados.size());
        
        // Estatísticas por gravidade
        long vermelhos = filaPrioridade.stream().filter(p -> p.getGravidade() == 1).count();
        long amarelos = filaPrioridade.stream().filter(p -> p.getGravidade() == 2).count();
        long verdes = filaPrioridade.stream().filter(p -> p.getGravidade() == 3).count();
        
        stats.put("vermelhos", vermelhos);
        stats.put("amarelos", amarelos);
        stats.put("verdes", verdes);
        
        return stats;
    }
    
    /**
     * Remove paciente do sistema (apenas se não estiver em atendimento)
     */
    public synchronized boolean removerPaciente(String cpf) {
        Paciente paciente = pacientesPorCpf.get(cpf);
        if (paciente == null || "ATENDIDO".equals(paciente.getStatus())) {
            return false;
        }
        
        // Remove de todas as estruturas
        pacientesPorCpf.remove(cpf);
        filaEspera.removeIf(p -> p.getCpf().equals(cpf));
        filaPrioridade.removeIf(p -> p.getCpf().equals(cpf));
        pacientesFinalizados.removeIf(p -> p.getCpf().equals(cpf));
        
        return true;
    }
}