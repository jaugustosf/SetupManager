package com.jaugustosf.setup_manager.repository;

import com.jaugustosf.setup_manager.model.Equipamento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    Optional<Equipamento> findByNome(String nome);
}