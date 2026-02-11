package com.cookMaster.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseStatusException handleNotFoundException(NotFoundException ex) {
        log.error("NotFoundException: {}, cause: {}, \n trace: {}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }

    @ExceptionHandler(FunctionalException.class)
    public ResponseStatusException handleFunctionalException(FunctionalException ex) {
        log.error("FunctionalException: {}, cause: {}, \n trace: {}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseStatusException handleTechnicalException(TechnicalException ex) {
        log.error("TechnicalException: {}, cause: {}, \n trace: {}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseStatusException handleUnauthorizedException(UnauthorizedException ex) {
        log.error("UnauthorizedException: {}, cause: {}, \n trace: {}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseStatusException handleResourceConflictException(ResourceConflictException ex) {
        log.error("ResourceConflictException: {}, cause: {}, \n trace: {}", ex.getLocalizedMessage(), ex.getCause(), ex.getStackTrace());
        return new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
    }
    }

