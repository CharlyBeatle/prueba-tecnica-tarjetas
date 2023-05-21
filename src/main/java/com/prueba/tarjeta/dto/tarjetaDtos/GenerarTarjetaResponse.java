package com.prueba.tarjeta.dto.tarjetaDtos;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerarTarjetaResponse {

    private String message;
}
