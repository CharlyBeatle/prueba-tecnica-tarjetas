package com.prueba.tarjeta.dto.tarjetaDtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivarTarjetaResponse {

    private String message;
    private Long numeroTarjeta;
    private String nombreCliente;
    private String apellidoCliente;
    private LocalDate fechaActivacion;
    private LocalDate fechaVencimiento;
}
