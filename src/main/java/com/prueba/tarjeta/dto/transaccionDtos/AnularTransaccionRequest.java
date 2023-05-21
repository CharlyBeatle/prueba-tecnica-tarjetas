package com.prueba.tarjeta.dto.transaccionDtos;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnularTransaccionRequest {

    @NotNull(message = "El numero de tarjeta es requerido")
    private Long numeroTarjeta;

    @NotNull(message = "El id de la transacción es requerido")
    private Integer idTransaccion;
}
