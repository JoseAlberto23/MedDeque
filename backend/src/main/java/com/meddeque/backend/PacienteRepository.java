package com.meddeque.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findAllByOrderByGravidadeAsc();
}
