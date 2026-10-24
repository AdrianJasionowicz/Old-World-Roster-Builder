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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
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

    private void userExists(boolean enabled) {
        when(loginUserService.loadUserByUsername("skaven"))
                .thenReturn(User.withUsername("skaven").password("encoded").roles("USER").disabled(!enabled).build());
    }

    @Test
    @DisplayName("A valid token in the jwt cookie logs the user in")
    void cookieToken() throws Exception {
        userExists(true);
        var request = new MockHttpServletRequest();
        request.setCookies(new Cookie(JwtUtil.COOKIE_NAME, jwtUtil.generateToken("skaven")));

        var chain = filter(request);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertEquals("skaven", authentication.getName());
        assertTrue(authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
        assertNotNull(chain.getRequest());
    }

    @Test
    @DisplayName("A valid bearer token in the Authorization header logs the user in")
    void bearerToken() throws Exception {
        userExists(true);
        var request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtUtil.generateToken("skaven"));

        filter(request);

        assertEquals("skaven", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    @DisplayName("The Authorization header wins over the cookie")
    void headerBeforeCookie() throws Exception {
        userExists(true);
        var request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtUtil.generateToken("skaven"));
        request.setCookies(new Cookie(JwtUtil.COOKIE_NAME, jwtUtil.generateToken("dwarf")));

        filter(request);

        assertEquals("skaven", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(loginUserService, never()).loadUserByUsername("dwarf");
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
        var request = new MockHttpServletRequest();
        request.setCookies(new Cookie(JwtUtil.COOKIE_NAME, "not-a-jwt"));

        var chain = filter(request);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertNotNull(chain.getRequest());
        verifyNoInteractions(loginUserService);
    }

    @Test
    @DisplayName("A disabled user is not logged in")
    void disabledUser() throws Exception {
        userExists(false);
        var request = new MockHttpServletRequest();
        request.setCookies(new Cookie(JwtUtil.COOKIE_NAME, jwtUtil.generateToken("skaven")));

        filter(request);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("A token of a deleted user passes on without a user and without an error")
    void deletedUser() throws Exception {
        when(loginUserService.loadUserByUsername("skaven")).thenThrow(new UsernameNotFoundException("not found"));
        var request = new MockHttpServletRequest();
        request.setCookies(new Cookie(JwtUtil.COOKIE_NAME, jwtUtil.generateToken("skaven")));

        var chain = filter(request);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertNotNull(chain.getRequest());
    }
}
