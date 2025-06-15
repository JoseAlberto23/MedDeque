package com.meddeque.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MedDeque - Sistema Inteligente de Triagem Médica
 * 
 * Sistema desenvolvido com estruturas de dados otimizadas:
 * - Heap (Min-Heap) para gerenciamento de prioridades
 * - Hash Table para buscas rápidas por CPF
 * - Queue (FIFO) para fila de espera
 * 
 * @author MedDeque Team
 * @version 1.0.0
 */
@SpringBootApplication
public class MedDequeApplication {
    
    public static void main(String[] args) {
        System.out.println("🏥 Iniciando MedDeque - Sistema Inteligente de Triagem Médica");
        System.out.println("📊 Estruturas de dados utilizadas:");
        System.out.println("   • Heap (Min-Heap) - Gerenciamento de prioridades");
        System.out.println("   • Hash Table - Buscas rápidas por CPF");
        System.out.println("   • Queue (FIFO) - Fila de espera para triagem");
        System.out.println("🚀 Sistema iniciado com sucesso!");
        
        SpringApplication.run(MedDequeApplication.class, args);
    }
}