package com.beaconfire.aop;


import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.exception.CustomGeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomGeneralException.class})
    public ResponseEntity<?> handleCustomGeneralException(CustomGeneralException ex) {
        System.out.println("GlobalExceptionHandler caught: " + ex.getClass());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse<>(GeneralResponse.Status.FAILED, ex.getMessage(), "CustomGeneralException Caught by: " + this.getClass()));
    }

    // you can add more methods to handle other types of exceptions

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException ex) {

        System.out.println("GlobalExceptionHandler caught: " + ex.getClass());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GeneralResponse<>(GeneralResponse.Status.FAILED, ex.getMessage(), "NumberFormatException Caught by: " + this.getClass()));
    }

    // 404 will not get to here
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<?> handleNotFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.status(404).body(new GeneralResponse<String>(GeneralResponse.Status.FAILED, "404 Page not found", "caught by: " + this.getClass()));
    }
}

