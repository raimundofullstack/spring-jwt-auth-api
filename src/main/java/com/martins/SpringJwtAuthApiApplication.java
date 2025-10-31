package com.martins;

import com.martins.model.RoleModel;
import com.martins.model.UserModel;
import com.martins.repository.RoleRepository;
import com.martins.repository.UserRepository;
import com.martins.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class SpringJwtAuthApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJwtAuthApiApplication.class, args);
	}

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(RoleRepository roleRepository, UserRepository userRepository, UserService userService){
        return args -> {
            if (roleRepository.count() == 0 && userRepository.count() == 0) {
                List<RoleModel> roles = List.of(
                        new RoleModel("ROLE_ADMIN"),
                        new RoleModel("ROLE_USER"),
                        new RoleModel("ROLE_MASTER")
                );
                roleRepository.saveAll(roles);

                // ====== USUÁRIOS ======
                for (int i = 1; i <= 30; i++) {
                    String banco = "nubank";
                    String nome = "Usuário " + i + " - " + banco;
                    String email = "user" + i + "@" + banco + ".com";
                    String password = "senha" + i;

                    UserModel user = new UserModel();
                    user.setId(UUID.randomUUID().toString());
                    user.setName(nome);
                    user.setEmail(email);
                    user.setPassword(password);
                    userService.save(user);

                    // Define a role conforme o intervalo
                    String roleName;
                    if (i <= 10) {
                        roleName = "ROLE_ADMIN";
                    } else if (i <= 20) {
                        roleName = "ROLE_USER";
                    } else {
                        roleName = "ROLE_MASTER";
                    }

                    userService.addRoleToUser(email, roleName);
                }
            }
        };
    }
}
