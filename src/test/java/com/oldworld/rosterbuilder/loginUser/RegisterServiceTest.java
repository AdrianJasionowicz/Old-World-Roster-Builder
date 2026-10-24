package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.exception.ConflictException;
import com.oldworld.rosterbuilder.loginUser.model.RegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    LoginUserRepository loginUserRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    RegisterService registerService;

    private final RegisterRequest request = new RegisterRequest("skaven", "secret", "skaven@test.com");

    @Test
    @DisplayName("createNewLoginUser saves an enabled user with an encoded password and the user role")
    void createNewLoginUser() {
        when(passwordEncoder.encode("secret")).thenReturn("encoded");
        var captor = ArgumentCaptor.forClass(LoginUser.class);

        registerService.createNewLoginUser(request);

        verify(loginUserRepository).save(captor.capture());
        var saved = captor.getValue();
        assertEquals("skaven", saved.getUsername());
        assertEquals("encoded", saved.getPassword());
        assertEquals("skaven@test.com", saved.getEmail());
        assertEquals(Role.ROLE_USER, saved.getRole());
        assertTrue(saved.isEnabled());
    }

    @Test
    @DisplayName("createNewLoginUser rejects a taken username and saves nothing")
    void createNewLoginUserWithTakenUsername() {
        when(loginUserRepository.existsByUsername("skaven")).thenReturn(true);

        var exception = assertThrows(ConflictException.class, () -> registerService.createNewLoginUser(request));

        assertEquals("Username is already taken", exception.getMessage());
        verify(loginUserRepository, never()).save(any());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("createNewLoginUser rejects a taken email and saves nothing")
    void createNewLoginUserWithTakenEmail() {
        when(loginUserRepository.existsByEmail("skaven@test.com")).thenReturn(true);

        var exception = assertThrows(ConflictException.class, () -> registerService.createNewLoginUser(request));

        assertEquals("Email is already taken", exception.getMessage());
        verify(loginUserRepository, never()).save(any());
    }

    @Test
    @DisplayName("createNewLoginUser passes a database error on")
    void createNewLoginUserWithDatabaseError() {
        when(passwordEncoder.encode("secret")).thenReturn("encoded");
        when(loginUserRepository.save(any())).thenThrow(new IllegalStateException("database down"));

        assertThrows(IllegalStateException.class, () -> registerService.createNewLoginUser(request));
    }
}
