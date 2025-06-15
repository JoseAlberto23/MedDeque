package com.meddeque.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    @Query("SELECT p FROM Paciente p ORDER BY p.gravidade ASC, p.dataHoraCadastro ASC")
    List<Paciente> findAllByOrderByGravidadeAscDataHoraCadastroAsc();
    
    List<Paciente> findByGravidadeOrderByDataHoraCadastroAsc(int gravidade);
    
    boolean existsByCpf(String cpf);
    
    List<Paciente> findByNomeContainingIgnoreCase(String nome);
}