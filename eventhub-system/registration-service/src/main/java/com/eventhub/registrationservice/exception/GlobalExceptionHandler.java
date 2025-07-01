package com.eventhub.registrationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<?> handleEventFullException(EventFullException ex,WebRequest webRequest){
        return buildResponse(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<?> handleAlreadyRegisteredException(AlreadyRegisteredException ex, WebRequest request){
        return buildResponse(ex.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?>handleGeneral(Exception ex,WebRequest request){
        return buildResponse("Something went wrong:"+ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String,Object>> buildResponse(String message,HttpStatus status){
        Map<String,Object> response = new HashMap<>();
        response.put("message",message);
        response.put("status",status);
        response.put("error",status.getReasonPhrase());
        response.put("timestamp",LocalDateTime.now());

        return new ResponseEntity<>(response,status);
    }
}
