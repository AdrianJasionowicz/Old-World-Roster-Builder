package com.armybuilderv2.armyBuilderV2.exception;

import com.armybuilderv2.armyBuilderV2.exception.base.BusinessRuleException;
import com.armybuilderv2.armyBuilderV2.exception.base.ResourceConflictException;
import com.armybuilderv2.armyBuilderV2.exception.base.ResourceNotFoundException;
import com.armybuilderv2.armyBuilderV2.exception.model.ApiErrorResponse;
import com.armybuilderv2.armyBuilderV2.exception.model.FieldErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                request,
                List.of()
        );
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessRuleViolation(
            BusinessRuleException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                request,
                List.of()
        );
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceConflict(
            ResourceConflictException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
                HttpStatus.CONFLICT,
                request,
                List.of()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        List<FieldErrorResponse> fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorResponse(
                        error.getField(),
                        error.getDefaultMessage()))
                .toList();
        return buildErrorResponse(
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                request,
                fieldErrors
        );
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(
            String message,
            HttpStatus status,
            HttpServletRequest request,
            List<FieldErrorResponse> errors) {
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                message,
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ArmyAccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleArmyAccessDenied(
            ArmyAccessDeniedException exception,
            HttpServletRequest request) {

        return buildErrorResponse(
                exception.getMessage(),
                HttpStatus.FORBIDDEN,
                request,
                List.of()
        );
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handleWrongPassword(
            WrongPasswordException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED,
                request,
                List.of()
        );
    }

    @ExceptionHandler(UserNoLoggedInException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNoLoggedIn(
            UserNoLoggedInException exception,
            HttpServletRequest request) {
        return buildErrorResponse(
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED,
                request,
                List.of()
        );
    }
}