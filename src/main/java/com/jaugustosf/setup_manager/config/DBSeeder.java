package com.jaugustosf.setup_manager.config;

import com.jaugustosf.setup_manager.model.Equipamento;
import com.jaugustosf.setup_manager.repository.EquipamentoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class DBSeeder implements CommandLineRunner {

    private final EquipamentoRepository repository;

    public DBSeeder(EquipamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll(); //Reseta o banco sempre.

        if (repository.count() == 0) {
            System.out.println("🌱 Banco vazio. Populando dados iniciais...");

            Equipamento e1 = new Equipamento("VXE R1 Pro", "Mouse");
            Equipamento e2 = new Equipamento("Wooting 60HE", "Teclado");
            Equipamento e3 = new Equipamento("LG UltraGear 27", "Monitor");

            repository.saveAll(List.of(e1, e2, e3));
            System.out.println("✅ Mock finalizado!");
        } else {
            System.out.println("ℹ️ Banco já contém dados. Pulando o Seeder.");
        }
    }
}