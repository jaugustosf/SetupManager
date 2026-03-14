package com.jaugustosf.setup_manager.repository;

import com.jaugustosf.setup_manager.model.Equipment;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findByName(String name);
    Page<Equipment> findByCategoryIgnoreCase(String category, Pageable pageable);
}