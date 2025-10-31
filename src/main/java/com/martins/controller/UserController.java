package com.martins.controller;

import com.martins.model.UserModel;
import com.martins.repository.UserRepository;
import com.martins.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private UserRepository userRepository;

    @GetMapping
    public List<UserModel> findAll() {
        return this.userRepository.findAll();
    }

    @PostMapping
    public UserModel save(@Valid @RequestBody UserModel userModel) {
        // Validação manual
        if (userModel.getName() == null || userModel.getName().isBlank() ||
                userModel.getEmail() == null || userModel.getEmail().isBlank() ||
                userModel.getPassword() == null || userModel.getPassword().isBlank()) {
            throw new IllegalArgumentException("Campos obrigatórios ausentes: name, email ou password");
        }
        return this.userRepository.save(userModel);
    }


}
