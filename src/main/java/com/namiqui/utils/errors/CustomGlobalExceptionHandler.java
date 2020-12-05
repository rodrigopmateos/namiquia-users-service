package com.namiqui.utils.errors;

import com.namiqui.models.ErrorDTO;
import com.namiqui.models.ResponseGeneric;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ResponseGeneric response = new ResponseGeneric();
        response.setTimestamp(new Date());
        response.setCodeStatus(status.value());

        //Map<String, Object> body = new LinkedHashMap<>();
        //body.put("timestamp", new Date());
        //body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        //body.put("errors", errors);
        response.setErrors(new ErrorDTO(errors.toString()));

        return new ResponseEntity<>(response, headers, status);
    }
}
