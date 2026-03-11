package com.jaugustosf.setup_manager.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jaugustosf.setup_manager.model.Equipment;

@JsonPropertyOrder({"name", "category"})
public record EquipmentResponseDTO(String name, String category) {

    public EquipmentResponseDTO(Equipment equipment) {
        this(equipment.getName(), equipment.getCategory());
    }
}