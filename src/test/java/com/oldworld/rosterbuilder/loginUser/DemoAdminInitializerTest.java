package com.oldworld.rosterbuilder.loginUser;

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
class DemoAdminInitializerTest {

    @Mock
    LoginUserRepository loginUserRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    DemoAdminInitializer demoAdminInitializer;

    @Test
    @DisplayName("run creates the demo admin with an encoded password")
    void createsDemoAdmin() {
        when(passwordEncoder.encode("123")).thenReturn("encoded");
        var captor = ArgumentCaptor.forClass(LoginUser.class);

        demoAdminInitializer.run(null);

        verify(loginUserRepository).save(captor.capture());
        var admin = captor.getValue();
        assertEquals("Adrian", admin.getUsername());
        assertEquals("encoded", admin.getPassword());
        assertEquals("adrian@example.com", admin.getEmail());
        assertEquals(Role.ROLE_ADMIN, admin.getRole());
        assertTrue(admin.isEnabled());
    }

    @Test
    @DisplayName("run leaves an existing Adrian account untouched")
    void keepsExistingUser() {
        when(loginUserRepository.existsByUsername("Adrian")).thenReturn(true);

        demoAdminInitializer.run(null);

        verify(loginUserRepository, never()).save(any());
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    @DisplayName("run does not create a second account with a taken email")
    void keepsExistingEmail() {
        when(loginUserRepository.existsByEmail("adrian@example.com")).thenReturn(true);

        demoAdminInitializer.run(null);

        verify(loginUserRepository, never()).save(any());
    }

    @Test
    @DisplayName("run passes a database error on")
    void databaseError() {
        when(passwordEncoder.encode("123")).thenReturn("encoded");
        when(loginUserRepository.save(any())).thenThrow(new IllegalStateException("database down"));

        assertThrows(IllegalStateException.class, () -> demoAdminInitializer.run(null));
    }
}
