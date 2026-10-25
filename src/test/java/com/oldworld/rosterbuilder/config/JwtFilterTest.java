package com.oldworld.rosterbuilder.config;

import com.oldworld.rosterbuilder.loginUser.LoginUserService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    private final JwtUtil jwtUtil = new JwtUtil("test-secret-that-is-at-least-32-characters-long", false);

    @Mock
    LoginUserService loginUserService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private MockFilterChain filter(MockHttpServletRequest request) throws Exception {
        var chain = new MockFilterChain();
        new JwtFilter(jwtUtil, loginUserService).doFilter(request, new MockHttpServletResponse(), chain);
        return chain;
    }

    private void userExists(String username, boolean enabled) {
        when(loginUserService.loadUserByToken(username, "key-1"))
                .thenReturn(Optional.of(User.withUsername(username).password("encoded").roles("USER").disabled(!enabled).build()));
    }

    private static MockHttpServletRequest withCookie(String token) {
        var request = new MockHttpServletRequest();
        request.setCookies(new Cookie(JwtUtil.COOKIE_NAME, token));
        return request;
    }

    @Test
    @DisplayName("A valid token in the jwt cookie logs the user in")
    void cookieToken() throws Exception {
        userExists("skaven", true);

        var chain = filter(withCookie(jwtUtil.generateToken("skaven", "key-1")));

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertEquals("skaven", authentication.getName());
        assertTrue(authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
        assertNotNull(chain.getRequest());
    }

    @Test
    @DisplayName("A valid bearer token in the Authorization header logs the user in")
    void bearerToken() throws Exception {
        userExists("skaven", true);
        var request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtUtil.generateToken("skaven", "key-1"));

        filter(request);

        assertEquals("skaven", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    @DisplayName("The Authorization header wins over the cookie")
    void headerBeforeCookie() throws Exception {
        userExists("skaven", true);
        var request = withCookie(jwtUtil.generateToken("dwarf", "key-1"));
        request.addHeader("Authorization", "Bearer " + jwtUtil.generateToken("skaven", "key-1"));

        filter(request);

        assertEquals("skaven", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(loginUserService, never()).loadUserByToken("dwarf", "key-1");
    }

    @Test
    @DisplayName("A request without a token passes on without a user")
    void noToken() throws Exception {
        var request = new MockHttpServletRequest();
        request.setCookies(new Cookie("other", "value"));

        var chain = filter(request);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertNotNull(chain.getRequest());
        verifyNoInteractions(loginUserService);
    }

    @Test
    @DisplayName("An Authorization header that is not a bearer token is ignored")
    void basicHeader() throws Exception {
        var request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic c2thdmVuOnNlY3JldA==");

        filter(request);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verifyNoInteractions(loginUserService);
    }

    @Test
    @DisplayName("An invalid token passes on without a user")
    void invalidToken() throws Exception {
        var chain = filter(withCookie("not-a-jwt"));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertNotNull(chain.getRequest());
        verifyNoInteractions(loginUserService);
    }

    @Test
    @DisplayName("A disabled user is not logged in")
    void disabledUser() throws Exception {
        userExists("skaven", false);

        filter(withCookie(jwtUtil.generateToken("skaven", "key-1")));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("A revoked token, of a deleted user or with an old key, passes on without a user")
    void revokedToken() throws Exception {
        when(loginUserService.loadUserByToken(any(), any())).thenReturn(Optional.empty());

        var chain = filter(withCookie(jwtUtil.generateToken("skaven", "old-key")));

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertNotNull(chain.getRequest());
    }
}
