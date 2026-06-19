package com.armybuilderv2.armyBuilderV2.loginUser;

import com.armybuilderv2.armyBuilderV2.exception.EmptyRegisterRequestException;
import com.armybuilderv2.armyBuilderV2.exception.UsernameAlreadyTakenException;
import com.armybuilderv2.armyBuilderV2.loginUser.model.RegisterRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserService implements UserDetailsService {
private final LoginUserRepository loginUserRepository;

    public LoginUserService(LoginUserRepository loginUserRepository) {
        this.loginUserRepository = loginUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserDetails userDetails =  loginUserRepository.findByUsername(username);
       if (userDetails == null) {
           throw new UsernameNotFoundException("User with username: " + username + " was not found");
       }
        return userDetails;
    }


}
