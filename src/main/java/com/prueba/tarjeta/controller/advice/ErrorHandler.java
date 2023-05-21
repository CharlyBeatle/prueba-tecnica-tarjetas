package com.prueba.tarjeta.controller.advice;

import com.prueba.tarjeta.dto.errors.ErrorDetalleDto;
import com.prueba.tarjeta.dto.errors.ExceptionDeSistema;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ExceptionDeSistema.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ResponseEntity<ErrorDetalleDto> resourceNotFoundException(ExceptionDeSistema ex) {

        return ResponseEntity.status(ex.getHttpStatus())
                .body(ErrorDetalleDto.builder()
                        .code(ex.getCode())
                        .description(ex.getDescription())
                        .tecError(ex.getTecError())
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDetalleDto> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private List<ErrorDetalleDto> processFieldErrors(List<FieldError> fieldErrors) {
        List<ErrorDetalleDto> errores = new ArrayList<>();
        for (FieldError fieldError: fieldErrors) {
            errores.add(ErrorDetalleDto.builder()
                    .code(fieldError.getField())
                    .description(fieldError.getDefaultMessage())
                    .tecError("VALIDATION_ERROR")
                    .build());
        }
        return errores;
    }
}
