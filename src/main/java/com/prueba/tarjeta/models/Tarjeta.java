package com.prueba.tarjeta.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tarjetas_credito")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Integer idTarjeta;

    @Column(name = "cliente_id")
    private Integer idCliente;

    @Column(name = "numero_tarjeta")
    private Long numeroTarjeta;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "fecha_activacion")
    private LocalDate fechaActivacion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "estado")
    private String estado;
}
