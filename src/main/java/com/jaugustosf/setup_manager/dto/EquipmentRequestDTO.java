package com.jaugustosf.setup_manager.dto;

import com.jaugustosf.setup_manager.model.Equipment;
import jakarta.validation.constraints.NotBlank;

public record EquipmentRequestDTO(
        @NotBlank(message = "The equipment name cannot be empty!")
        String name,

        @NotBlank(message = "The equipment category is mandatory!")
        String category
) {

    public Equipment convertToEntity() {
        Equipment equipment = new Equipment();
        equipment.setName(this.name());
        equipment.setCategory(this.category());
        return equipment;
    }
}