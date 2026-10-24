package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.exception.ConflictException;
import com.oldworld.rosterbuilder.exception.ForbiddenException;
import com.oldworld.rosterbuilder.loginUser.model.DeleteAccountRequest;
import com.oldworld.rosterbuilder.loginUser.model.EmailChangeRequest;
import com.oldworld.rosterbuilder.loginUser.model.PasswordChangeRequest;
import com.oldworld.rosterbuilder.loginUser.model.UsernameChangeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class SettingsService {

    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;
    private final LoginUserRepository loginUserRepository;

    @Transactional
    void changePassword(PasswordChangeRequest request) {
        var loginUser = getCurrentUserConfirmedBy(request.password());
        loginUser.setPassword(passwordEncoder.encode(request.newPassword()));
        log.info("User '{}' changed password", loginUser.getUsername());
    }

    @Transactional
    void changeEmail(EmailChangeRequest request) {
        var loginUser = getCurrentUserConfirmedBy(request.password());
        loginUser.setEmail(request.newEmail());
        log.info("User '{}' changed email", loginUser.getUsername());
    }

    @Transactional
    void changeUsername(UsernameChangeRequest request) {
        var loginUser = getCurrentUserConfirmedBy(request.password());
        if (loginUserRepository.existsByUsername(request.newUsername())) {
            throw new ConflictException("Username already exists");
        }
        log.info("User '{}' changed username to '{}'", loginUser.getUsername(), request.newUsername());
        loginUser.setUsername(request.newUsername());
    }

    @Transactional
    void deleteAccount(DeleteAccountRequest request) {
        var loginUser = getCurrentUserConfirmedBy(request.password());
        loginUserRepository.delete(loginUser);
        log.info("User '{}' deleted their account", loginUser.getUsername());
    }

    private LoginUser getCurrentUserConfirmedBy(String password) {
        var loginUser = currentUserService.getCurrentUser();
        if (!passwordEncoder.matches(password, loginUser.getPassword())) {
            log.warn("User '{}' confirmed an account change with a wrong password", loginUser.getUsername());
            throw new ForbiddenException("Wrong password");
        }
        return loginUser;
    }
}
