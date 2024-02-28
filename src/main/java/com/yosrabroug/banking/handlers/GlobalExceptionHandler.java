package com.yosrabroug.banking.handlers;

import com.yosrabroug.banking.exceptions.ObjectValidatorException;
import com.yosrabroug.banking.exceptions.OperationNotpermittedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//on dit à spring que c'est notre gestionnaire globale d'exceptions
public class GlobalExceptionHandler {
    //capter la première exception
    @ExceptionHandler(ObjectValidatorException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(ObjectValidatorException exception){
        // on a transformé l'exception "exception" en une représentation qu'on
        //peut exposé à l'utilisateur.
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("Object not valid exception has occured")
                .errorSource(exception.getViolationSource())
                .validationErrors(exception.getViolations())
                .build();
        //renvoyer un objet JSON
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(representation);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(EntityNotFoundException exception){
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(representation);


    }

    @ExceptionHandler(OperationNotpermittedException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(OperationNotpermittedException exception){
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getErrorMsg())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(representation);


    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionRepresentation> handleException() {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("A user already exists with this email")
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(representation);

    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionRepresentation> handleDisabledException() {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("You cannot access your account because it is not yet activated")
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionRepresentation> handleBadCredentialException() {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("Your email/password is incorrect")
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(representation);

    }


    }
