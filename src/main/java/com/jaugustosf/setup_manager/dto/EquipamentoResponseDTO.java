package com.jaugustosf.setup_manager.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jaugustosf.setup_manager.model.Equipamento;

// 1. Trocamos 'class' por 'record'.
// 2. Declaramos os atributos direto nos parênteses. Eles já nascem privados, finais e com "getters" automáticos!
@JsonPropertyOrder({"nome", "categoria"})
public record EquipamentoResponseDTO(String nome, String categoria) {

    // 3. O construtor customizado que recebe a Entidade e chama o construtor principal do record
    public EquipamentoResponseDTO(Equipamento equipamento) {
        this(equipamento.getNome(), equipamento.getCategoria());
    }
}