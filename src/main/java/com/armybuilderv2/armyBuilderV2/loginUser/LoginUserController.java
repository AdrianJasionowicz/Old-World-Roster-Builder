package com.armybuilderv2.armyBuilderV2.loginUser;

import com.armybuilderv2.armyBuilderV2.config.JwtUtil;
import com.armybuilderv2.armyBuilderV2.loginUser.model.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginUserController {
    private final RegisterService registerService;
    private final SettingsService settingsService;
    private LoginUserService loginUserService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;


    public LoginUserController(LoginUserService loginUserService, RegisterService registerService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, SettingsService settingsService) {
        this.loginUserService = loginUserService;
        this.registerService = registerService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.settingsService = settingsService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        registerService.createNewLoginUser(registerRequest);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(request.username());

            ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(86400)
                    .build();

            response.setHeader("Set-Cookie", cookie.toString());

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Błędny login lub hasło");
        }
    }


    @GetMapping("/me")
    public ResponseEntity<LoginStatus> me() {
        return ResponseEntity.ok(loginUserService.getLoginStatus());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response ) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        settingsService.changePassword(passwordChangeRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changeEmail")
    public ResponseEntity<?> changeEmail(@RequestBody EmailChangeRequest emailChangeRequest) {
        settingsService.changeEmail(emailChangeRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changeUsername")
    public ResponseEntity<?> changeUsername(@RequestBody UsernameChangeRequest usernameChangeRequest) {
        settingsService.changeUsername(usernameChangeRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<?> deleteAccount(@RequestBody DeleteAccountRequest deleteAccountRequest) {
        settingsService.deleteAccount(deleteAccountRequest);
        return ResponseEntity.ok().build();
    }

}
