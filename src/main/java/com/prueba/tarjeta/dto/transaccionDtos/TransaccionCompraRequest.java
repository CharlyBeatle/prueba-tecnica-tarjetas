package com.prueba.tarjeta.dto.transaccionDtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransaccionCompraRequest {

    @NotNull(message = "El numero de tarjeta es requerido")
    private Long numeroTarjeta;

    @NotNull(message = "El valor de la compra es requerido")
    private BigDecimal price;
}
