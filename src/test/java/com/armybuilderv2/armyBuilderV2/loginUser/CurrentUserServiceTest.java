package com.armybuilderv2.armyBuilderV2.loginUser;

import com.armybuilderv2.armyBuilderV2.exception.UserNoLoggedInException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.swing.text.html.Option;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrentUserServiceTest {

    @Mock
    LoginUserRepository loginUserRepository;
    @InjectMocks
    CurrentUserService currentUserService;


    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentUser() {

        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("admin");
        loginUser.setPassword("admin");
        loginUser.setRole(Role.ROLE_ADMIN);
        loginUser.setId(1L);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "admin",
                        "admin",
                        List.of()
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        when(loginUserRepository.findByUsername("admin"))
                .thenReturn(Optional.of(loginUser));

        assertTrue(authentication.isAuthenticated());

        assertEquals(
                loginUser,
                currentUserService.getCurrentUser()
        );
    }


//    public LoginUser getCurrentUser() {
//        Authentication authentication =
//                SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new UserNoLoggedInException("User not logged in");
//        }
//
//        String username = authentication.getName();
//
//        return loginUserRepository.findByUsername(username).orElseThrow(() -> new UserNoLoggedInException("User Not Logged In"));
//
//    }

    @Test
    void validateArmyAccess() {
    }
}