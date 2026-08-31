package com.armybuilderv2.armyBuilderV2.loginUser;

import com.armybuilderv2.armyBuilderV2.exception.EmailAlreadyTakenException;
import com.armybuilderv2.armyBuilderV2.exception.EmptyRegisterRequestException;
import com.armybuilderv2.armyBuilderV2.exception.UsernameAlreadyTakenException;
import com.armybuilderv2.armyBuilderV2.loginUser.model.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RegisterService {
    private final LoginUserRepository loginUserRepository;
    private PasswordEncoder passwordEncoder;

    public RegisterService(LoginUserRepository loginUserRepository, PasswordEncoder passwordEncoder) {
        this.loginUserRepository = loginUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createNewLoginUser(RegisterRequest registerRequest) {
        if (registerRequest == null) {
            throw new EmptyRegisterRequestException("Empty register request");
        }
        LoginUser loginUser = new LoginUser();
        boolean isUsernameTaken = loginUserRepository.existsByUsername(registerRequest.username());
        if (!isUsernameTaken) {
            loginUser.setUsername(registerRequest.username());
        } else {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        loginUser.setPassword(passwordEncoder.encode(registerRequest.password()));
        boolean isEmailTaken = loginUserRepository.existsByEmail(registerRequest.email());
        if (!isEmailTaken) {
            loginUser.setEmail(registerRequest.email());
        } else {
            throw new EmailAlreadyTakenException("Email is already taken");
        }
        loginUser.setRole(Role.ROLE_ADMIN);
        loginUser.setArmies(new ArrayList<>());
        loginUser.setEnabled(true);
        loginUserRepository.save(loginUser);
    }
}
