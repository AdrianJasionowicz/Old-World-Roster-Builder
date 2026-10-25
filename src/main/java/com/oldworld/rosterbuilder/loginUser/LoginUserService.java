package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.loginUser.model.LoginStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUserService implements UserDetailsService {

    private final LoginUserRepository loginUserRepository;
    private final CurrentUserService currentUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginUserRepository.findByUsername(username)
                .map(LoginUserPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " was not found"));
    }

    public Optional<UserDetails> loadUserByToken(String username, String tokenKey) {
        return loginUserRepository.findByUsername(username)
                .filter(loginUser -> loginUser.getTokenKey().equals(tokenKey))
                .map(LoginUserPrincipal::from);
    }

    LoginStatus getLoginStatus() {
        var loginUser = currentUserService.getCurrentUser();
        return new LoginStatus(loginUser.getUsername(), loginUser.getRole(), loginUser.getEmail());
    }
}
