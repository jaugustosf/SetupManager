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
        mockMvc.perform(get("/equipment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("VXE R1 Pro"));
    }

    @Test
    @DisplayName("Should return 404 and our custom message when searching for non-existent ID")
    void searchError404() throws Exception {
        mockMvc.perform(get("/equipment/999"))
                .andExpect(status().isNotFound())
                // Validating if our GlobalExceptionHandler is working!
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Should return 400 when trying to register equipment without name")
    void registerValidationError() throws Exception {
        String invalidJson = "{\"name\": \"\", \"category\": \"Test\"}";

        mockMvc.perform(post("/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return a paginated list when searching for equipment by partial name")
    void searchByNamePagedSuccess() throws Exception {
        mockMvc.perform(get("/equipment?name=Wooting&page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Wooting 60HE"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should return all equipments paginated when no filters are applied")
    void listAllPagedSuccess() throws Exception {
        mockMvc.perform(get("/equipment?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(3));
    }
}