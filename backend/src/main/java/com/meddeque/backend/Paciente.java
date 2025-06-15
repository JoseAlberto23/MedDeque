package com.meddeque.backend;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String cpf;
    
    @Column(nullable = false, length = 1000)
    private String sintomas;
    
    @Column(nullable = false)
    private int gravidade;
    
    @Column(nullable = false)
    private LocalDateTime dataHoraCadastro;

    @PrePersist
    protected void onCreate() {
        dataHoraCadastro = LocalDateTime.now();
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
    public void setGravidade(int gravidade) { this.gravidade = gravidade; }

    public LocalDateTime getDataHoraCadastro() { return dataHoraCadastro; }
    public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) { this.dataHoraCadastro = dataHoraCadastro; }
}