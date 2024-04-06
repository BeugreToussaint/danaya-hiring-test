package de.tuxbe.testtechnique.web.exception;

import de.tuxbe.testtechnique.web.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalException {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        log.error("message error : {}", ex.getMessage());
        return error;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorMessage handleException(HttpRequestMethodNotSupportedException exception, WebRequest webRequest) {
        ErrorMessage error = new ErrorMessage(
                HttpStatus.METHOD_NOT_ALLOWED.value(), LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false)
        );
        log.error("message error : {}", exception.getMessage());
        return error;

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleException(ResourceNotFoundException ex, WebRequest webRequest) {
        ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        log.error("message error : {}", ex.getMessage());
        return error;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleException(NoResourceFoundException ex, WebRequest webRequest) {
        ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        log.error("message error : {}", ex.getMessage());
        return error;
    }


    @ExceptionHandler(TimeoutException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ErrorMessage handleException(TimeoutException exception, WebRequest webRequest) {
        ErrorMessage error = new ErrorMessage(
                HttpStatus.REQUEST_TIMEOUT.value(), LocalDateTime.now(), exception.getMessage(), webRequest.getDescription(false)
        );
        log.error("message error : {}", exception.getMessage());
        return error;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleException(IllegalArgumentException ex, WebRequest webRequest) {
        ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        log.error("message error : {}", ex.getMessage());
        return error;
    }

    @ExceptionHandler(ResourceExistantException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleException(ResourceExistantException ex, WebRequest webRequest) {
        ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        log.error("message error : {}", ex.getMessage());
        return error;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleException(DataIntegrityViolationException ex, WebRequest webRequest) {

        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException violationException = (ConstraintViolationException) ex.getCause();
            String constraintName = violationException.getErrorMessage();
            if (constraintName != null && constraintName.contains("unique_number_identity")){
                int uniqueNumberIdentity = constraintName.indexOf("unique_number_identity");
                log.error("Constraint error : {}", constraintName.substring(uniqueNumberIdentity - 1));
                return new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), LocalDateTime.now(), constraintName.substring(uniqueNumberIdentity - 1), webRequest.getDescription(false));
            }

        }
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY.value(), LocalDateTime.now(), "Violation de l'intégrité des données", webRequest.getDescription(false));
        log.error("message error : {}", ex.getMessage());
        return errorMessage;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleException(Exception ex, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), ex.getMessage(), webRequest.getDescription(false));
        log.error("message error : {}", ex.getMessage());
        return errorMessage;
    }
}
