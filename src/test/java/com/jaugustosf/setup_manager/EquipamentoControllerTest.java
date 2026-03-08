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
class EquipamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve retornar 200 ao buscar um equipamento existente")
    void buscarSucesso() throws Exception {
        // Como o DBSeeder já inseriu dados, o ID 1 deve existir
        mockMvc.perform(get("/equipamentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("VXE R1 Pro"));
    }

    @Test
    @DisplayName("Deve retornar 404 e nossa mensagem customizada ao buscar ID inexistente")
    void buscarErro404() throws Exception {
        mockMvc.perform(get("/equipamentos/999"))
                .andExpect(status().isNotFound())
                // Validamos se o nosso TratadorDeErros está funcionando!
                .andExpect(jsonPath("$.mensagem").exists());
    }

    @Test
    @DisplayName("Deve retornar 400 ao tentar cadastrar equipamento sem nome")
    void cadastrarErroValidacao() throws Exception {
        String jsonInvalido = "{\"nome\": \"\", \"categoria\": \"Teste\"}";

        mockMvc.perform(post("/equipamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }
}