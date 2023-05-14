package com.beaconfire.aop;


import com.beaconfire.domain.DTO.GeneralResponse;
import com.beaconfire.exception.CustomGeneralException;
import com.beaconfire.exception.CustomSomethingNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    @Around("execution(* com.beaconfire.controller.*.*(..))")
    public ResponseEntity<?> handleCustomGeneralException(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            //is essentially invoking the method that the @Around advice is applied to
            return (ResponseEntity<?>) joinPoint.proceed();
        } catch (CustomGeneralException ex) {
            System.out.println("ExceptionHandlingAspect Caught: " +ex.getClass());
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "Caught CustomGeneralException", ex.getMessage()));
        } catch (NumberFormatException ex){
            return ResponseEntity.badRequest().body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "Caught NumberFormatException", ex.getMessage()));
        } catch (CustomSomethingNotFoundException ex){
            return ResponseEntity.status(404).body(new GeneralResponse<>(GeneralResponse.Status.FAILED, "Caught " + ex.getClass(), ex.getMessage()));
        }
    }
}

