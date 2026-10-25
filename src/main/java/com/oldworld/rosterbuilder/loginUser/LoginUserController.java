package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.config.JwtUtil;
import com.oldworld.rosterbuilder.loginUser.model.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
class LoginUserController {

    private final RegisterService registerService;
    private final SettingsService settingsService;
    private final LoginUserService loginUserService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        registerService.createNewLoginUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
            var principal = (LoginUserPrincipal) authentication.getPrincipal();
            log.info("User '{}' logged in", principal.username());
            return withCookie(jwtUtil.createAuthCookie(principal.username(), principal.tokenKey()).toString());
        } catch (AuthenticationException e) {
            log.warn("Failed login for user '{}'", request.username());
            throw e;
        }
    }

    @GetMapping("/me")
    ResponseEntity<LoginStatus> me() {
        return ResponseEntity.ok(loginUserService.getLoginStatus());
    }

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@AuthenticationPrincipal LoginUserPrincipal principal) {
        if (principal != null) {
            settingsService.logout(principal.username());
        }
        return withCookie(jwtUtil.clearAuthCookie().toString());
    }

    @PatchMapping("/account/password")
    ResponseEntity<Void> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        return withCookie(authCookie(settingsService.changePassword(request)));
    }

    @PatchMapping("/account/email")
    ResponseEntity<Void> changeEmail(@Valid @RequestBody EmailChangeRequest request) {
        settingsService.changeEmail(request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/account/username")
    ResponseEntity<Void> changeUsername(@Valid @RequestBody UsernameChangeRequest request) {
        return withCookie(authCookie(settingsService.changeUsername(request)));
    }

    @DeleteMapping("/account")
    ResponseEntity<Void> deleteAccount(@Valid @RequestBody DeleteAccountRequest request) {
        settingsService.deleteAccount(request);
        return withCookie(jwtUtil.clearAuthCookie().toString());
    }

    private String authCookie(LoginUser loginUser) {
        return jwtUtil.createAuthCookie(loginUser.getUsername(), loginUser.getTokenKey()).toString();
    }

    private ResponseEntity<Void> withCookie(String cookie) {
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie)
                .build();
    }
}
