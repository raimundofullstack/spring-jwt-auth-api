# Spring JWT Auth API
![Java](https://img.shields.io/badge/Java-17+-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green?logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-✔-orange?logo=spring&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-✔-green?logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-auth0-yellow?logo=JSONwebtokens&logoColor=white)
![BCrypt](https://img.shields.io/badge/BCrypt-✔-blue)
![H2 Database](https://img.shields.io/badge/H2-Database-lightgrey?logo=H2database&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-✔-blue?logo=apachemaven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-✔-red?logo=lombok&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-✔-blue?logo=docker&logoColor=white)
![Docker Compose](https://img.shields.io/badge/Docker%20Compose-✔-blue?logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-CI/CD-blue?logo=githubactions&logoColor=white)
![Docker Hub](https://img.shields.io/badge/Docker%20Hub-Published-blue?logo=docker&logoColor=white)
![CI](https://github.com/raimundofullstack/spring-jwt-auth-api/actions/workflows/ci-cd.yml/badge.svg)

API de autenticação e autorização com Spring Boot, Spring Security e JPA.

Este projeto demonstra a implementação completa de **login, registro, autenticação JWT** e **controle de acesso baseado em roles**, servindo como exemplo prático de boas práticas em segurança e arquitetura de APIs REST juntamente com testes unitários e integrados(JUnit 5 - Jupiter).

---

## 🚀 Tecnologias

- ☕ Java 17+
- 🌱 Spring Boot 3
- 🔒 Spring Security
- 🗄️ Spring Data JPA / Hibernate
- 📝 JWT (JSON Web Token com auth0/java-jwt)
- 🔑 BCrypt
- 💾 H2 Database (para testes)
- 📦 Maven
- ✨ Lombok
- 🐳 Docker
- 📦 Docker Compose
- ⚙️ GitHub Actions (CI/CD)
- 🏷️ Docker Hub

## 🚀 Testes

- **JUnit 5 (Jupiter)**
- **Mockito**
- **Spring Boot Test / MockMvc**
- **ObjectMapper (Jackson)**

---

## ⚙️ Funcionalidades

- Registro de usuários
- Login e geração de token JWT
- Autenticação baseada em token
- Controle de acesso por perfil (ROLE_USER / ROLE_ADMIN)
- Endpoints públicos e privados
- Tratamento de exceções customizado

---

## 🧱 Estrutura do Projeto

src/main/java/com/martins

├── security/ # Configurações de segurança e JWT

├── controller/ # Endpoints REST

├── model/ # Data Transfer Objects

├── entity/ # Entidades JPA

├── repository/ # Interfaces do JPA

├── service/ # Regras de negócio

└── filter/ # Filtros e providers JWT

## 🧩 Endpoints Principais

| Método | Endpoint           | Descrição                    | Permissão       |
|--------|--------------------|------------------------------|-----------------|
| POST   | `/api/auth0/token` | Login e geração de token     | Público         |
| POST   | `/api/users`       | Criar um novo usuário        | MASTER e ADMIN     |
| GET    | `/api/roles`       | Lista de niveis de permissão | MASTER e ADMIN |
| GET    | `/api/users`       | Lista de usuários            | MASTER e ADMIN     |

---

## 🔐 Exemplo de Token JWT
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...

## 🧰 Como Rodar

````bash
# Clonar o repositório
git clone https://github.com/raimundofullstack/spring-jwt-auth-api.git
cd spring-jwt-auth-api

# Rodar aplicação
./mvnw spring-boot:run

````
A API será iniciada em http://localhost:8080.

## 🧠 Aprendizados

Este projeto demonstra:
-Configuração do Spring Security sem WebSecurityConfigurerAdapter

-Uso de filtros personalizados (AuthenticationFilter, AuthorizationFilter)

-Persistência de usuários com JPA

-Geração e validação de tokens JWT

## 📚 Próximos Passos

-Adicionar refresh token

-Implementar logout e blacklist de tokens

-Substituir H2 por PostgreSQL ou AWS


👨‍💻 Autor

Raimundo Martins | 
Desenvolvedor Full Stack
