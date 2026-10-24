package com.oldworld.rosterbuilder.exception;

import com.oldworld.rosterbuilder.exception.model.FieldErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final MockHttpServletRequest request = new MockHttpServletRequest("GET", "/armies/1");

    @Test
    @DisplayName("BusinessRuleException is a 400 with its message and the request path")
    void businessRule() {
        var response = handler.handleBusinessRuleViolation(new BusinessRuleException("Factions do not match"), request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().status());
        assertEquals("Factions do not match", response.getBody().message());
        assertEquals("/armies/1", response.getBody().path());
        assertNotNull(response.getBody().timestamp());
        assertTrue(response.getBody().errors().isEmpty());
    }

    @Test
    @DisplayName("A failed validation is a 400 listing every wrong field")
    void validation() {
        var bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new FieldError("request", "name", "must not be blank"));
        bindingResult.addError(new FieldError("request", "pointsLimit", "must be greater than 0"));
        var exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        var response = handler.handleMethodArgumentNotValid(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed", response.getBody().message());
        assertEquals(List.of(
                new FieldErrorResponse("name", "must not be blank"),
                new FieldErrorResponse("pointsLimit", "must be greater than 0")), response.getBody().errors());
    }

    @Test
    @DisplayName("An unreadable body is a 400 without parser details")
    void messageNotReadable() {
        var exception = new HttpMessageNotReadableException("JSON parse error at line 1", new MockHttpInputMessage(new byte[0]));

        var response = handler.handleMessageNotReadable(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Malformed request body", response.getBody().message());
    }

    @Test
    @DisplayName("A wrong path parameter is a 400 naming the parameter")
    void typeMismatch() {
        var exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("armyId");

        var response = handler.handleTypeMismatch(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid value for parameter 'armyId'", response.getBody().message());
    }

    @Test
    @DisplayName("Wrong credentials are a 401 with a generic message")
    void badCredentials() {
        var response = handler.handleAuthenticationFailed(new BadCredentialsException("Bad credentials for skaven"), request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody().message());
    }

    @Test
    @DisplayName("Other authentication errors are a 401 asking to log in")
    void authenticationRequired() {
        var response = handler.handleAuthenticationFailed(new InsufficientAuthenticationException("Full authentication required"), request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Authentication required", response.getBody().message());
    }

    @Test
    @DisplayName("UnauthorizedException is a 401 with its message")
    void unauthorized() {
        var response = handler.handleUnauthorized(new UnauthorizedException("User not logged in"), request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("User not logged in", response.getBody().message());
    }

    @Test
    @DisplayName("ForbiddenException is a 403 with its message")
    void forbidden() {
        var response = handler.handleForbidden(new ForbiddenException("Wrong password"), request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Wrong password", response.getBody().message());
    }

    @Test
    @DisplayName("Spring Security access denied is a 403 with a generic message")
    void accessDenied() {
        var response = handler.handleAccessDenied(new AccessDeniedException("ROLE_ADMIN required"), request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access denied", response.getBody().message());
    }

    @Test
    @DisplayName("NotFoundException is a 404 with its message")
    void notFound() {
        var response = handler.handleNotFound(new NotFoundException("Army with id 1 not found"), request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Army with id 1 not found", response.getBody().message());
    }

    @Test
    @DisplayName("ConflictException is a 409 with its message")
    void conflict() {
        var response = handler.handleConflict(new ConflictException("Username is already taken"), request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Username is already taken", response.getBody().message());
    }

    @Test
    @DisplayName("A foreign key violation is a 409 without database details")
    void dataIntegrityViolation() {
        var response = handler.handleDataIntegrityViolation(new DataIntegrityViolationException("fk_army_unit_unit"), request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Resource is still referenced by other data", response.getBody().message());
    }

    @Test
    @DisplayName("A Spring error with its own status keeps that status and detail")
    void springErrorResponse() {
        var response = handler.handleUnexpected(new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed"), request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals("Method not allowed", response.getBody().message());
    }

    @Test
    @DisplayName("An unexpected error is a 500 that hides the cause")
    void unexpected() {
        var response = handler.handleUnexpected(new IllegalStateException("password=secret"), request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().status());
        assertEquals("Unexpected server error", response.getBody().message());
        assertEquals("/armies/1", response.getBody().path());
    }
}
