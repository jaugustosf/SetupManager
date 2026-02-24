package com.jaugustosf.setup_manager.repository;

import com.jaugustosf.setup_manager.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

// Estendemos o JpaRepository. Ele pede duas coisas: <A Classe que ele vai cuidar, O tipo de dado do ID dessa classe>
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    // Só de herdar essa interface, nós ganhamos métodos como save(), findAll(), findById() e deleteById() de GRAÇA.
    // O Spring vai gerar o código Java e o SQL para isso automaticamente quando o servidor iniciar!
}