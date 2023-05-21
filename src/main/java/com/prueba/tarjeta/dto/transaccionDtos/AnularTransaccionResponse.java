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
public class AnularTransaccionResponse {

    private String message;
    private Long numeroTarjeta;
    private String nombre;
    private String apellido;
    private BigDecimal saldoAnterior;
    private BigDecimal compra;
    private BigDecimal saldoActual;
    private LocalDateTime fechaTransaccion;
}
