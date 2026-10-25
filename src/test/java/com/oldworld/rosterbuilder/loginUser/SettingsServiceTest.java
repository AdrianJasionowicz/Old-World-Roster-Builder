package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.exception.ConflictException;
import com.oldworld.rosterbuilder.exception.ForbiddenException;
import com.oldworld.rosterbuilder.exception.UnauthorizedException;
import com.oldworld.rosterbuilder.loginUser.model.DeleteAccountRequest;
import com.oldworld.rosterbuilder.loginUser.model.EmailChangeRequest;
import com.oldworld.rosterbuilder.loginUser.model.PasswordChangeRequest;
import com.oldworld.rosterbuilder.loginUser.model.UsernameChangeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SettingsServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    CurrentUserService currentUserService;
    @Mock
    LoginUserRepository loginUserRepository;
    @InjectMocks
    SettingsService settingsService;

    private LoginUser loggedIn(boolean correctPassword) {
        var loginUser = new LoginUser();
        loginUser.setUsername("skaven");
        loginUser.setPassword("encoded");
        loginUser.setEmail("skaven@test.com");
        when(currentUserService.getCurrentUser()).thenReturn(loginUser);
        when(passwordEncoder.matches("password", "encoded")).thenReturn(correctPassword);
        return loginUser;
    }

    private void notLoggedIn() {
        when(currentUserService.getCurrentUser()).thenThrow(new UnauthorizedException("User not logged in"));
    }

    @Test
    @DisplayName("changePassword stores the new encoded password")
    void changePassword() {
        var loginUser = loggedIn(true);
        var oldKey = loginUser.getTokenKey();
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncoded");

        var result = settingsService.changePassword(new PasswordChangeRequest("password", "newPassword"));

        assertSame(loginUser, result);
        assertEquals("newEncoded", loginUser.getPassword());
        assertNotEquals(oldKey, loginUser.getTokenKey());
    }

    @Test
    @DisplayName("changePassword with a wrong password is forbidden and keeps the old password and tokens")
    void changePasswordWrongPassword() {
        var loginUser = loggedIn(false);
        var oldKey = loginUser.getTokenKey();

        assertThrows(ForbiddenException.class, () -> settingsService.changePassword(new PasswordChangeRequest("password", "newPassword")));

        assertEquals("encoded", loginUser.getPassword());
        assertEquals(oldKey, loginUser.getTokenKey());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    @DisplayName("changePassword without a logged in user throws UnauthorizedException")
    void changePasswordNotLoggedIn() {
        notLoggedIn();

        assertThrows(UnauthorizedException.class, () -> settingsService.changePassword(new PasswordChangeRequest("password", "newPassword")));
    }

    @Test
    @DisplayName("changeEmail stores the new email")
    void changeEmail() {
        var loginUser = loggedIn(true);

        settingsService.changeEmail(new EmailChangeRequest("password", "new@test.com"));

        assertEquals("new@test.com", loginUser.getEmail());
    }

    @Test
    @DisplayName("changeEmail with a wrong password is forbidden and keeps the old email")
    void changeEmailWrongPassword() {
        var loginUser = loggedIn(false);

        assertThrows(ForbiddenException.class, () -> settingsService.changeEmail(new EmailChangeRequest("password", "new@test.com")));

        assertEquals("skaven@test.com", loginUser.getEmail());
    }

    @Test
    @DisplayName("changeEmail without a logged in user throws UnauthorizedException")
    void changeEmailNotLoggedIn() {
        notLoggedIn();

        assertThrows(UnauthorizedException.class, () -> settingsService.changeEmail(new EmailChangeRequest("password", "new@test.com")));
    }

    @Test
    @DisplayName("changeUsername stores a free username")
    void changeUsername() {
        var loginUser = loggedIn(true);
        var oldKey = loginUser.getTokenKey();
        when(loginUserRepository.existsByUsername("clanrat")).thenReturn(false);

        var result = settingsService.changeUsername(new UsernameChangeRequest("clanrat", "password"));

        assertSame(loginUser, result);
        assertEquals("clanrat", loginUser.getUsername());
        assertNotEquals(oldKey, loginUser.getTokenKey());
    }

    @Test
    @DisplayName("changeUsername to a taken username is a conflict and keeps the old username and tokens")
    void changeUsernameTaken() {
        var loginUser = loggedIn(true);
        var oldKey = loginUser.getTokenKey();
        when(loginUserRepository.existsByUsername("clanrat")).thenReturn(true);

        var exception = assertThrows(ConflictException.class, () -> settingsService.changeUsername(new UsernameChangeRequest("clanrat", "password")));

        assertEquals("Username already exists", exception.getMessage());
        assertEquals("skaven", loginUser.getUsername());
        assertEquals(oldKey, loginUser.getTokenKey());
    }

    @Test
    @DisplayName("logout revokes every token of the user")
    void logout() {
        var loginUser = new LoginUser();
        var oldKey = loginUser.getTokenKey();
        when(loginUserRepository.findByUsername("skaven")).thenReturn(Optional.of(loginUser));

        settingsService.logout("skaven");

        assertNotEquals(oldKey, loginUser.getTokenKey());
    }

    @Test
    @DisplayName("logout of a user that no longer exists does nothing")
    void logoutDeletedUser() {
        when(loginUserRepository.findByUsername("skaven")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> settingsService.logout("skaven"));
    }

    @Test
    @DisplayName("logout passes a database error on")
    void logoutDatabaseError() {
        when(loginUserRepository.findByUsername("skaven")).thenThrow(new IllegalStateException("database down"));

        assertThrows(IllegalStateException.class, () -> settingsService.logout("skaven"));
    }

    @Test
    @DisplayName("changeUsername with a wrong password is forbidden and does not check the new username")
    void changeUsernameWrongPassword() {
        var loginUser = loggedIn(false);

        assertThrows(ForbiddenException.class, () -> settingsService.changeUsername(new UsernameChangeRequest("clanrat", "password")));

        assertEquals("skaven", loginUser.getUsername());
        verify(loginUserRepository, never()).existsByUsername(any());
    }

    @Test
    @DisplayName("deleteAccount deletes the current user")
    void deleteAccount() {
        var loginUser = loggedIn(true);

        settingsService.deleteAccount(new DeleteAccountRequest("password"));

        verify(loginUserRepository).delete(loginUser);
    }

    @Test
    @DisplayName("deleteAccount with a wrong password is forbidden and deletes nothing")
    void deleteAccountWrongPassword() {
        loggedIn(false);

        var exception = assertThrows(ForbiddenException.class, () -> settingsService.deleteAccount(new DeleteAccountRequest("password")));

        assertEquals("Wrong password", exception.getMessage());
        verify(loginUserRepository, never()).delete(any());
    }

    @Test
    @DisplayName("deleteAccount without a logged in user throws UnauthorizedException")
    void deleteAccountNotLoggedIn() {
        notLoggedIn();

        assertThrows(UnauthorizedException.class, () -> settingsService.deleteAccount(new DeleteAccountRequest("password")));

        verifyNoInteractions(loginUserRepository);
    }
}
