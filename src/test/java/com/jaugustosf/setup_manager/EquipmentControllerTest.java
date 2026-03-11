package com.jaugustosf.setup_manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return 200 when searching for an existing equipment")
    void searchSuccess() throws Exception {
        // Assuming DBSeeder has inserted data, ID 1 should exist
        mockMvc.perform(get("/equipments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("VXE R1 Pro"));
    }

    @Test
    @DisplayName("Should return 404 and our custom message when searching for non-existent ID")
    void searchError404() throws Exception {
        mockMvc.perform(get("/equipments/999"))
                .andExpect(status().isNotFound())
                // Validating if our GlobalExceptionHandler is working!
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Should return 400 when trying to register equipment without name")
    void registerValidationError() throws Exception {
        String invalidJson = "{\"name\": \"\", \"category\": \"Test\"}";

        mockMvc.perform(post("/equipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}