package com.armybuilderv2.armyBuilderV2.loginUser;

import com.armybuilderv2.armyBuilderV2.loginUser.model.LoginStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService implements UserDetailsService {
private final LoginUserRepository loginUserRepository;


    public LoginUserService(LoginUserRepository loginUserRepository) {
        this.loginUserRepository = loginUserRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = loginUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " was not found"));
        return userDetails;
    }


    public LoginStatus getLoginStatus() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LoginUser loginUser = loginUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " was not found"));
        Role role = loginUser.getRole();
        return new LoginStatus(username, role, loginUser.getEmail());
    }


}
