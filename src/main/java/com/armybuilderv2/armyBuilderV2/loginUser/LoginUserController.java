package com.armybuilderv2.armyBuilderV2.loginUser;

import com.armybuilderv2.armyBuilderV2.config.JwtUtil;
import com.armybuilderv2.armyBuilderV2.loginUser.model.LoginRequest;
import com.armybuilderv2.armyBuilderV2.loginUser.model.RegisterRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginUserController {
    private final RegisterService registerService;
    private LoginUserService loginUserService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;


    public LoginUserController(LoginUserService loginUserService, RegisterService registerService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.loginUserService = loginUserService;
        this.registerService = registerService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
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
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(request.getUsername());

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
}
