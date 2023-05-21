package com.prueba.tarjeta.objetosMock;

import com.prueba.tarjeta.dto.tarjetaDtos.ActivarTarjetaRequest;
import com.prueba.tarjeta.dto.tarjetaDtos.RecargaSaldoRequest;
import com.prueba.tarjeta.dto.transaccionDtos.AnularTransaccionRequest;
import com.prueba.tarjeta.dto.transaccionDtos.ConsultaTransaccionResponse;
import com.prueba.tarjeta.dto.transaccionDtos.TransaccionCompraRequest;
import com.prueba.tarjeta.models.Tarjeta;
import com.prueba.tarjeta.models.Transaccion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ObjetosMock {

    public static TransaccionCompraRequest requestCompra(){
        return TransaccionCompraRequest.builder()
                .numeroTarjeta(1020301729791975L)
                .price(BigDecimal.valueOf(5))
                .build();
    }

    public static AnularTransaccionRequest requestAnular(){
        return AnularTransaccionRequest.builder()
                .idTransaccion(1)
                .numeroTarjeta(103040596865L)
                .build();
    }

    public static ActivarTarjetaRequest requestActivar(){
        return ActivarTarjetaRequest.builder()
                .numeroTarjeta(1030678090L)
                .build();
    }

    public static RecargaSaldoRequest requestRecarga(){
        return RecargaSaldoRequest.builder()
                .numeroTarjeta(1249128749187L)
                .balance(BigDecimal.valueOf(10))
                .build();
    }

    public static Tarjeta tarjeta(){
        return Tarjeta.builder()
                .idTarjeta(1)
                .idCliente(1)
                .numeroTarjeta(1020301729791975L)
                .fechaCreacion(LocalDate.now())
                .fechaActivacion(LocalDate.now())
                .fechaVencimiento(LocalDate.now())
                .saldo(BigDecimal.valueOf(45))
                .estado("Activo")
                .build();
    }

    public static Transaccion transaccion(){
        return Transaccion.builder()
                .idTransaccion(5)
                .idTarjeta(1)
                .precioTransaccion(BigDecimal.valueOf(5))
                .estadoTransaccion("Realizada")
                .detalle("Compra")
                .fechaTransaccion(LocalDateTime.now())
                .build();
    }
}
