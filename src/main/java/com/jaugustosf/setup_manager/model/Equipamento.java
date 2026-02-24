package com.jaugustosf.setup_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity // 1. Avisa o Hibernate: "Transforme esta classe em uma Tabela no banco!"
public class Equipamento {

    @Id // 2. Avisa que este campo é a Chave Primária (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 3. Avisa que o Banco de Dados vai gerar o ID automaticamente (o AUTO_INCREMENT / SERIAL)
    private Long id;

    // Blindando os atributos!
    @NotBlank(message = "O nome do equipamento não pode estar vazio!")
    private String nome;

    @NotBlank(message = "A categoria do equipamento é obrigatória!")
    private String categoria;

    // Construtor vazio (OBRIGATÓRIO para a JPA conseguir ler do banco)
    public Equipamento() {}

    public Equipamento(Long id, String nome, String categoria) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
    }

    // Getters e Setters (Essenciais)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}