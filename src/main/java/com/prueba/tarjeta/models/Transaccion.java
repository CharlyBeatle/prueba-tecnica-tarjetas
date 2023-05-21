package com.prueba.tarjeta.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @Column(name = "id_tarjeta")
    private Integer idTarjeta;

    @Column(name = "precio_transaccion")
    private BigDecimal precioTransaccion;

    @Column(name = "estado_transaccion")
    private String estadoTransaccion;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "fecha_transaccion")
    private LocalDateTime fechaTransaccion;

}
