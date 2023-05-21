package com.prueba.tarjeta.dto.errors;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionDeSistema extends RuntimeException{

    private static final Long ID = 1L;
    private String code;
    private String description;
    private HttpStatus httpStatus;
    private String tecError;
}
