package com.jaugustosf.setup_manager.repository;

import com.jaugustosf.setup_manager.model.Equipment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findByName(String name);
    List<Equipment> findByCategoryIgnoreCase(String category);
}