package com.prueba.tarjeta.dto.tarjetaDtos;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecargaSaldoRequest {

    @NotNull(message = "El numero de tarjeta es requerido")
    private Long numeroTarjeta;

    @NotNull(message = "La cantidad a recargar es requerida")
    @Min(value = 1, message = "El valor mínimo de recarga de saldo es 1")
    private BigDecimal balance;
}
