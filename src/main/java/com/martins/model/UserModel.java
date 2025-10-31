package com.martins.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="users")
public class UserModel {

    @Id
    private String id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @Column(unique = true)
    @Email(message = "Email inválido")
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleModel> roles;

    public UserModel(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    private void prePersist(){
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
}
