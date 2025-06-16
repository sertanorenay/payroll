package dev.sertan.payroll.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeNotFoundAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeNotFoundException.class)
    public String employeeNotFoundHandler(EmployeeNotFoundException e) {
        return e.getMessage();
    }
}
