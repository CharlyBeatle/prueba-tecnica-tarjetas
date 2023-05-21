package com.prueba.tarjeta.dto.transaccionDtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaTransaccionResponse {

    private String message;
    private String estado;
    private String detalle;
    private BigDecimal valor;
    private LocalDateTime fechaTransaccion;

}
