package com.prueba.tarjeta.dto.tarjetaDtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecargarSaldoResponse {

    private String message;
    private Long numeroTarjeta;
    private String nombreCliente;
    private String apellidoCliente;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoActual;
}
