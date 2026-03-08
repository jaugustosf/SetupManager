package com.jaugustosf.setup_manager.repository;

import com.jaugustosf.setup_manager.model.Equipamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    Optional<Equipamento> findByNome(String nome);
    // O Spring entende: SELECT * FROM equipamentos WHERE categoria = :categoria
    List<Equipamento> findByCategoriaIgnoreCase(String categoria);
}