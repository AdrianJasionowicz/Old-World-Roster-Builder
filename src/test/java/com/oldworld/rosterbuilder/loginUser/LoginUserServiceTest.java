package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.exception.UnauthorizedException;
import com.oldworld.rosterbuilder.loginUser.model.LoginStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {

    @Mock
    LoginUserRepository loginUserRepository;
    @Mock
    CurrentUserService currentUserService;
    @InjectMocks
    LoginUserService loginUserService;

    private static LoginUser user(Role role, boolean enabled) {
        var loginUser = new LoginUser();
        loginUser.setUsername("skaven");
        loginUser.setPassword("encoded");
        loginUser.setEmail("skaven@test.com");
        loginUser.setRole(role);
        loginUser.setEnabled(enabled);
        return loginUser;
    }

    @Test
    @DisplayName("loadUserByUsername returns the user with its role as authority")
    void loadUserByUsername() {
        when(loginUserRepository.findByUsername("skaven")).thenReturn(Optional.of(user(Role.ROLE_ADMIN, true)));

        var userDetails = loginUserService.loadUserByUsername("skaven");

        assertEquals("skaven", userDetails.getUsername());
        assertEquals("encoded", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertEquals(List.of("ROLE_ADMIN"), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
    }

    @Test
    @DisplayName("loadUserByUsername keeps a disabled user disabled")
    void loadUserByUsernameDisabled() {
        when(loginUserRepository.findByUsername("skaven")).thenReturn(Optional.of(user(Role.ROLE_USER, false)));

        assertFalse(loginUserService.loadUserByUsername("skaven").isEnabled());
    }

    @Test
    @DisplayName("loadUserByUsername of an unknown user throws UsernameNotFoundException")
    void loadUserByUsernameNotFound() {
        when(loginUserRepository.findByUsername("nobody")).thenReturn(Optional.empty());

        var exception = assertThrows(UsernameNotFoundException.class, () -> loginUserService.loadUserByUsername("nobody"));

        assertEquals("User with username: nobody was not found", exception.getMessage());
    }

    @Test
    @DisplayName("getLoginStatus returns name, role and email of the current user")
    void getLoginStatus() {
        when(currentUserService.getCurrentUser()).thenReturn(user(Role.ROLE_USER, true));

        assertEquals(new LoginStatus("skaven", Role.ROLE_USER, "skaven@test.com"), loginUserService.getLoginStatus());
    }

    @Test
    @DisplayName("getLoginStatus of a user without an email has no email")
    void getLoginStatusWithoutEmail() {
        var loginUser = user(Role.ROLE_USER, true);
        loginUser.setEmail(null);
        when(currentUserService.getCurrentUser()).thenReturn(loginUser);

        assertNull(loginUserService.getLoginStatus().email());
    }

    @Test
    @DisplayName("getLoginStatus without a logged in user throws UnauthorizedException")
    void getLoginStatusNotLoggedIn() {
        when(currentUserService.getCurrentUser()).thenThrow(new UnauthorizedException("User not logged in"));

        assertThrows(UnauthorizedException.class, () -> loginUserService.getLoginStatus());
    }
}
