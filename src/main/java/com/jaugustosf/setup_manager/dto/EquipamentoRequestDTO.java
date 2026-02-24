package com.jaugustosf.setup_manager.dto;

import com.jaugustosf.setup_manager.model.Equipamento;
import jakarta.validation.constraints.NotBlank;

// As validações ficam coladas nos atributos aqui nos parênteses!
public record EquipamentoRequestDTO(
        @NotBlank(message = "O nome do equipamento não pode estar vazio!")
        String nome,

        @NotBlank(message = "A categoria do equipamento é obrigatória!")
        String categoria
) {

    // O nosso metodo utilitário continua aqui dentro para ajudar o Controller
    public Equipamento converterParaEntidade() {
        Equipamento equipamento = new Equipamento();

        // ATENÇÃO: Em records, os getters não têm a palavra "get".
        // É só o nome do atributo com parênteses!
        equipamento.setNome(this.nome());
        equipamento.setCategoria(this.categoria());

        return equipamento;
    }
}