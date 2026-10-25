package com.oldworld.rosterbuilder.loginUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "demo-admin.enabled", havingValue = "true")
@RequiredArgsConstructor
class DemoAdminInitializer implements ApplicationRunner {

    static final String USERNAME = "Adrian";
    static final String PASSWORD = "123";
    static final String EMAIL = "adrian@example.com";

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (loginUserRepository.existsByUsername(USERNAME) || loginUserRepository.existsByEmail(EMAIL)) {
            return;
        }
        var admin = new LoginUser();
        admin.setUsername(USERNAME);
        admin.setPassword(passwordEncoder.encode(PASSWORD));
        admin.setEmail(EMAIL);
        admin.setRole(Role.ROLE_ADMIN);
        admin.setEnabled(true);
        loginUserRepository.save(admin);
        log.warn("Created demo admin '{}' - turn DEMO_ADMIN off outside local development", USERNAME);
    }
}
