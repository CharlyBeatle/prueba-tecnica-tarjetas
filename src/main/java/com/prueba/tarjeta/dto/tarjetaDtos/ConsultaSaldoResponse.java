package com.prueba.tarjeta.dto.tarjetaDtos;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaSaldoResponse {

    private String message;
    private Long numeroTarjeta;
    private String nombreCliente;
    private String apellidoCliente;
    private BigDecimal saldo;
}
