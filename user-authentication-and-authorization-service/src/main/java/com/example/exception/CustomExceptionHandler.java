package com.example.exception;

import com.example.dto.ErrorDto;
import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        System.out.println("MethodArgumentNotValidException");
        System.out.println(ex.getClass());

        Map<String, String> result = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
                    String field = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    result.put(field, message);
                }
        );

        return ResponseEntity.badRequest().body(result);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        System.out.println("ConstraintViolationException");
        System.out.println(ex.getClass());
        Map<Path, String> result = new HashMap<>();

        ex.getConstraintViolations().forEach(constraintViolation -> {
            result.put(constraintViolation.getPropertyPath(), constraintViolation.getMessage());
        });

        return ResponseEntity.badRequest().body(result);
    }


    @ExceptionHandler(value = {AuthenticationException.class, JwtException.class})
    public ResponseEntity<ErrorDto> handleAuthenticationException(Exception ex, WebRequest request) {
        System.out.println("AuthenticationException or JwtException");
        System.out.println(ex.getClass());

        ErrorDto errorDto = new ErrorDto(ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAuthorizationException(Exception ex, WebRequest request) {
        System.out.println("AccessDeniedException");
        System.out.println(ex.getClass());

        ErrorDto errorDto = new ErrorDto(ex.getMessage() + ", you are not authorized to access this resource");
        return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleCustomException(CustomException ex, WebRequest request) {
        System.out.println("CustomException");
        System.out.println(ex.getClass());

        ErrorDto errorDto = new ErrorDto(ex.getMessage());
        return new ResponseEntity<>(errorDto, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGlobalException(Exception ex, WebRequest request) {
        System.out.println("INTERNAL_SERVER_ERROR");
        System.out.println(ex.getClass());

        ErrorDto errorDto = new ErrorDto(ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
