package com.meddeque.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "pacientes")
public class Paciente implements Comparable<Paciente> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    
    @Column(nullable = false, length = 1000)
    private String sintomas;
    
    @Column(nullable = false)
    private int gravidade; // 1=Vermelho, 2=Amarelo, 3=Verde
    
    @Column(nullable = false)
    private LocalDateTime dataHoraCadastro;
    
    @Column(nullable = false)
    private String status; // AGUARDANDO_TRIAGEM, TRIADO, ATENDIDO, FINALIZADO
    
    @Column
    private LocalDateTime dataHoraTriagem;
    
    @Column
    private LocalDateTime dataHoraAtendimento;
    
    public Paciente() {
        this.dataHoraCadastro = LocalDateTime.now();
        this.status = "AGUARDANDO_TRIAGEM";
    }
    
    public Paciente(String nome, String cpf, String sintomas) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.sintomas = sintomas;
    }
    
    // Implementação do Comparable para ordenação na heap
    @Override
    public int compareTo(Paciente outro) {
        // Prioridade: gravidade menor = maior prioridade
        if (this.gravidade != outro.gravidade) {
            return Integer.compare(this.gravidade, outro.gravidade);
        }
        // Em caso de empate, prioriza quem chegou primeiro
        return this.dataHoraCadastro.compareTo(outro.dataHoraCadastro);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente paciente = (Paciente) obj;
        return Objects.equals(cpf, paciente.cpf);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }
    
    public int getGravidade() { return gravidade; }
    public void setGravidade(int gravidade) { 
        this.gravidade = gravidade;
        if (this.dataHoraTriagem == null) {
            this.dataHoraTriagem = LocalDateTime.now();
            this.status = "TRIADO";
        }
    }
    
    public LocalDateTime getDataHoraCadastro() { return dataHoraCadastro; }
    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) { this.dataHoraCadastro = dataHoraCadastro; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getDataHoraTriagem() { return dataHoraTriagem; }
    public void setDataHoraTriagem(LocalDateTime dataHoraTriagem) { this.dataHoraTriagem = dataHoraTriagem; }
    
    public LocalDateTime getDataHoraAtendimento() { return dataHoraAtendimento; }
    public void setDataHoraAtendimento(LocalDateTime dataHoraAtendimento) { this.dataHoraAtendimento = dataHoraAtendimento; }
    
    public String getGravidadeTexto() {
        switch (gravidade) {
            case 1: return "Vermelho (Crítico)";
            case 2: return "Amarelo (Urgente)";
            case 3: return "Verde (Pouco Urgente)";
            default: return "Não Classificado";
        }
    }
}