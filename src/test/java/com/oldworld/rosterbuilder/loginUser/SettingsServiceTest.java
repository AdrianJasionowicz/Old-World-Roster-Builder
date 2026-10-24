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
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncoded");

        settingsService.changePassword(new PasswordChangeRequest("password", "newPassword"));

        assertEquals("newEncoded", loginUser.getPassword());
    }

    @Test
    @DisplayName("changePassword with a wrong password is forbidden and keeps the old password")
    void changePasswordWrongPassword() {
        var loginUser = loggedIn(false);

        assertThrows(ForbiddenException.class, () -> settingsService.changePassword(new PasswordChangeRequest("password", "newPassword")));

        assertEquals("encoded", loginUser.getPassword());
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
        when(loginUserRepository.existsByUsername("clanrat")).thenReturn(false);

        settingsService.changeUsername(new UsernameChangeRequest("clanrat", "password"));

        assertEquals("clanrat", loginUser.getUsername());
    }

    @Test
    @DisplayName("changeUsername to a taken username is a conflict and keeps the old username")
    void changeUsernameTaken() {
        var loginUser = loggedIn(true);
        when(loginUserRepository.existsByUsername("clanrat")).thenReturn(true);

        var exception = assertThrows(ConflictException.class, () -> settingsService.changeUsername(new UsernameChangeRequest("clanrat", "password")));

        assertEquals("Username already exists", exception.getMessage());
        assertEquals("skaven", loginUser.getUsername());
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
