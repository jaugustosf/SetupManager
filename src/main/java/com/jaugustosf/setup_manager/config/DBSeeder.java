package com.jaugustosf.setup_manager.config;

import com.jaugustosf.setup_manager.model.Equipment;
import com.jaugustosf.setup_manager.repository.EquipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class DBSeeder implements CommandLineRunner {

    private final EquipmentRepository repository;

    public DBSeeder(EquipmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll(); //Resets the database always.

        if (repository.count() == 0) {
            System.out.println("🌱 Database empty. Populating initial data...");

            Equipment e1 = new Equipment("VXE R1 Pro", "Mouse");
            Equipment e2 = new Equipment("Wooting 60HE", "Keyboard");
            Equipment e3 = new Equipment("LG UltraGear 27", "Monitor");

            repository.saveAll(List.of(e1, e2, e3));
            System.out.println("✅ Mock finished!");
        } else {
            System.out.println("ℹ️ Database already contains data. Skipping Seeder.");
        }
    }
}