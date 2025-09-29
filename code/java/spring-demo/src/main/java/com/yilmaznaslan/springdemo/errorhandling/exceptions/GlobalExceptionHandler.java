package com.yilmaznaslan.springdemo.errorhandling.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleVehicleNotFound(VehicleNotFoundException exception){
        final var message = String.format("Vehicle %d not found", exception.getVehicleId());
        final var errorResponse = new ErrorResponseDto("vehicleNotFound",message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        var errorResponse = new ErrorResponseDto(
                "validationFailed",
                String.join(", ", errors)
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
