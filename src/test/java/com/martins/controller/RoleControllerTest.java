package com.martins.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martins.filter.AuthenticationFilter;
import com.martins.filter.AuthorizationFilter;
import com.martins.model.RoleModel;
import com.martins.repository.RoleRepository;
import com.martins.repository.UserRepository;
import com.martins.security.JwtConfig;
import com.martins.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc(addFilters = false) // desativa filtros de segurança
class RoleControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(RoleControllerTest.class);

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RoleModel role;

    @MockBean
    private AuthorizationFilter authorizationFilter;

    @MockBean
    private AuthenticationFilter authenticationFilter;

    @MockBean
    private JwtConfig jwtConfig;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;


    @BeforeEach
    void setup() {
        role = new RoleModel();
        role.setId(1L);
        role.setName("ROLE_ADMIN");
    }

    // ========== TESTE GET /roles ==========
    @Test
    void testFindAll_ShouldReturnListOfRoles() throws Exception {
        when(roleRepository.findAll()).thenReturn(List.of(role));

        var result = mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andReturn();

        logger.info("➡️ Request GET /roles");
        logger.info("✅ Resultado esperado: lista com 1 role ROLE_ADMIN");
        logger.info("🧾 Resultado real: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result.getResponse().getContentAsString()));
    }

    // ========== TESTE POST /roles ==========
    @Test
    void testSave_ShouldCreateRole() throws Exception {
        when(roleRepository.save(any(RoleModel.class))).thenReturn(role);

        var result = mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)))
                .andExpect(status().isOk())
                .andReturn();

        logger.info("➡️ Request POST /roles: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(role));
        logger.info("✅ Resultado esperado: ROLE_ADMIN salvo");
        logger.info("🧾 Resultado real: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result.getResponse().getContentAsString()));
    }
}
