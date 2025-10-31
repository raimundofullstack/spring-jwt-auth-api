package com.martins.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martins.model.UserModel;
import com.martins.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // desativa filtros de segurança para simplificar
class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(RoleControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private UserModel user;

    @BeforeEach
    void setup() {
        // Limpa a tabela antes de cada teste
        userRepository.deleteAll();

        user = new UserModel();
        user.setId(UUID.randomUUID().toString());
        user.setName("Rai Martins");
        user.setEmail("rai@test.com");
        user.setPassword("1234");

        userRepository.save(user); // salva usuário inicial
    }

    @Test
    void testFindAll_ShouldReturnListOfUsers() throws Exception {
        var result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        // Log comparativo
        logger.info("➡️ Requisição GET /users");
        logger.info("✅ Esperado: lista com ao menos 1 usuário");
        logger.info("📄 Resultado real:\n" + content);

        // Valida que o usuário retornado é o que criamos
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$[0].email").value("rai@test.com"))
                .andExpect(jsonPath("$[0].name").value("Rai Martins"));
    }

    @Test
    void testSave_ShouldCreateUser() throws Exception {
        UserModel newUser = new UserModel();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setName("Novo Usuário");
        newUser.setEmail("novo@test.com");
        newUser.setPassword("5678");

        var result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        // Log comparativo
        logger.info("➡️ Requisição POST /users");
        logger.info("📄 Request body:\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newUser));
        logger.info("✅ Esperado: usuário criado");
        logger.info("📄 Resultado real:\n" + content);

        // Valida que o usuário foi realmente salvo no banco H2
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$[1].email").value("novo@test.com"))
                .andExpect(jsonPath("$[1].name").value("Novo Usuário"));
    }

}
