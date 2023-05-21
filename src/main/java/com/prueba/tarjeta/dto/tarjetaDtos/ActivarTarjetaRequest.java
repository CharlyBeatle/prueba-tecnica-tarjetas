package com.prueba.tarjeta.dto.tarjetaDtos;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivarTarjetaRequest {

    @NotNull(message = "El numero de la tarjeta es requerido")
    private Long numeroTarjeta;
}
