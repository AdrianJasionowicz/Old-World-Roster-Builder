package com.armybuilderv2.armyBuilderV2.loginUser;

import com.armybuilderv2.armyBuilderV2.exception.UsernameAlreadyTakenException;
import com.armybuilderv2.armyBuilderV2.exception.WrongPasswordException;
import com.armybuilderv2.armyBuilderV2.loginUser.model.DeleteAccountRequest;
import com.armybuilderv2.armyBuilderV2.loginUser.model.EmailChangeRequest;
import com.armybuilderv2.armyBuilderV2.loginUser.model.PasswordChangeRequest;
import com.armybuilderv2.armyBuilderV2.loginUser.model.UsernameChangeRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettingsService {
private final PasswordEncoder passwordEncoder;
private CurrentUserService currentUserService;
private LoginUserRepository loginUserRepository;

    public SettingsService(PasswordEncoder passwordEncoder, CurrentUserService currentUserService, LoginUserRepository loginUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;
        this.loginUserRepository = loginUserRepository;
    }


    @Transactional
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        LoginUser loginUser = currentUserService.getCurrentUser();
        if (passwordEncoder.matches(passwordChangeRequest.password(),loginUser.getPassword())) {
            loginUser.setPassword(passwordEncoder.encode(passwordChangeRequest.newPassword()));
        } else {
            throw new WrongPasswordException("Wrong password");
        }
    }

    @Transactional
    public void changeEmail(EmailChangeRequest emailChangeRequest) {
        LoginUser loginUser = currentUserService.getCurrentUser();
        if (passwordEncoder.matches(emailChangeRequest.password(),loginUser.getPassword())) {
            loginUser.setEmail(emailChangeRequest.newEmail());
        } else {
            throw new WrongPasswordException("Wrong password");
        }
    }

@Transactional
    public void changeUsername(UsernameChangeRequest usernameChangeRequest) {
        LoginUser loginUser = currentUserService.getCurrentUser();
        if (passwordEncoder.matches(usernameChangeRequest.password(),loginUser.getPassword())) {
            if (!loginUserRepository.existsByUsername(usernameChangeRequest.newUsername())) {
                loginUser.setUsername(usernameChangeRequest.newUsername());
            } else {
                throw new UsernameAlreadyTakenException("Username already exists");
            }
        } else {
            throw new WrongPasswordException("Wrong password");
        }
    }
@Transactional
    public void deleteAccount(DeleteAccountRequest deleteAccountRequest) {
        LoginUser loginUser = currentUserService.getCurrentUser();
        if (passwordEncoder.matches(deleteAccountRequest.password(),loginUser.getPassword())) {
            loginUserRepository.delete(loginUser);
        } else {
                throw new WrongPasswordException("Wrong password");
            }
    }
}
