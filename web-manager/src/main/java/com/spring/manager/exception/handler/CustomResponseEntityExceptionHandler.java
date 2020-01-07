package com.spring.manager.exception.handler;

import com.spring.manager.exception.EmailHasDuplicateException;
import com.spring.manager.exception.ProjectIdentifierException;
import com.spring.manager.exception.ProjectNotFoundException;
import com.spring.manager.exception.response.EmailHasDuplicateExceptionResponse;
import com.spring.manager.exception.response.ProjectIdentifierExceptionResponse;
import com.spring.manager.exception.response.ProjectNotFoundExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdentifierException ex, WebRequest request) {
        ProjectIdentifierExceptionResponse exceptionResponse = new ProjectIdentifierExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request) {
        ProjectNotFoundExceptionResponse projectNotFoundExceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleEmailHasDuplicateException(EmailHasDuplicateException ex, WebRequest request) {
        EmailHasDuplicateExceptionResponse projectNotFoundExceptionResponse = new EmailHasDuplicateExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(projectNotFoundExceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
