package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.exception.ConflictException;
import com.oldworld.rosterbuilder.loginUser.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class RegisterService {

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;

    void createNewLoginUser(RegisterRequest registerRequest) {
        if (loginUserRepository.existsByUsername(registerRequest.username())) {
            throw new ConflictException("Username is already taken");
        }
        if (loginUserRepository.existsByEmail(registerRequest.email())) {
            throw new ConflictException("Email is already taken");
        }
        var loginUser = new LoginUser();
        loginUser.setUsername(registerRequest.username());
        loginUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        loginUser.setEmail(registerRequest.email());
        loginUser.setRole(Role.ROLE_USER);
        loginUser.setEnabled(true);
        loginUserRepository.save(loginUser);
        log.info("Registered user '{}'", loginUser.getUsername());
    }
}
