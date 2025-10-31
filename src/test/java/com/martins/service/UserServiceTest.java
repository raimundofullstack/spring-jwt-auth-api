package com.martins.service;

import com.martins.model.RoleModel;
import com.martins.model.UserModel;
import com.martins.repository.RoleRepository;
import com.martins.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserModel user;
    private RoleModel role;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        role = new RoleModel();
        role.setId(1L);
        role.setName("ROLE_ADMIN");

        user = new UserModel();
        user.setId(UUID.randomUUID().toString());
        user.setEmail("user@test.com");
        user.setName("User Test");
        user.setPassword("1234");
        user.setRoles(new ArrayList<>());
    }

    // ========== TESTE 1 ==========
    @Test
    void testLoadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        // Arrange
        user.getRoles().add(role);
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));

        // Act
        UserDetails result = userService.loadUserByUsername("user@test.com");

        // Assert
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    // ========== TESTE 2 ==========
    @Test
    void testLoadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> userService.loadUserByUsername("notfound@test.com"));
    }

    // ========== TESTE 3 ==========
    @Test
    void testSave_ShouldEncodePasswordAndSaveUser() {
        // Arrange
        when(passwordEncoder.encode("1234")).thenReturn("encoded_password");
        when(userRepository.save(any(UserModel.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        UserModel savedUser = userService.save(user);

        // Assert
        assertEquals("encoded_password", savedUser.getPassword());
        verify(passwordEncoder, times(1)).encode("1234");
        verify(userRepository, times(1)).save(user);
    }

    // ========== TESTE 4 ==========
    @Test
    void testAddRoleToUser_ShouldAddRoleSuccessfully() {
        // Arrange
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
        when(userRepository.save(any(UserModel.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        userService.addRoleToUser("user@test.com", "ROLE_ADMIN");

        // Assert
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
        verify(userRepository, times(1)).save(user);
    }

    // ========== TESTE 5 ==========
    @Test
    void testAddRoleToUser_ShouldThrow_WhenUserNotFound() {
        when(userRepository.findByEmail("x@test.com")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.addRoleToUser("x@test.com", "ROLE_ADMIN"));
    }

    // ========== TESTE 6 ==========
    @Test
    void testAddRoleToUser_ShouldThrow_WhenRoleNotFound() {
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(roleRepository.findByName("ROLE_FAKE")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.addRoleToUser("user@test.com", "ROLE_FAKE"));
    }
}
