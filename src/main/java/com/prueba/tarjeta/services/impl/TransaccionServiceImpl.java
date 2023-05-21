package com.prueba.tarjeta.services.impl;

import com.prueba.tarjeta.dto.errors.ExceptionDeSistema;
import com.prueba.tarjeta.dto.tarjetaDtos.IClienteTarjeta;
import com.prueba.tarjeta.dto.transaccionDtos.*;
import com.prueba.tarjeta.models.Tarjeta;
import com.prueba.tarjeta.models.Transaccion;
import com.prueba.tarjeta.repositories.TarjetaRepository;
import com.prueba.tarjeta.repositories.TransaccionRepository;
import com.prueba.tarjeta.services.TransaccionService;
import com.prueba.tarjeta.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    @Autowired
    TransaccionRepository transaccionRepository;

    @Autowired
    TarjetaRepository tarjetaRepository;

    @Override
    public ResponseEntity<TransaccionCompraResponse> compra(TransaccionCompraRequest request) {
        try {
            Tarjeta tarjeta = tarjetaRepository.buscarTarjeta(request.getNumeroTarjeta());
            IClienteTarjeta cliente = tarjetaRepository.buscarClienteTarjeta(request.getNumeroTarjeta());

            if (tarjeta.getSaldo().compareTo(request.getPrice()) < 0){
                return ResponseEntity.ok(TransaccionCompraResponse.builder()
                                .message("El saldo en la tarjeta es insuficiente para hacer la compra")
                                .numeroTarjeta(tarjeta.getNumeroTarjeta())
                                .nombre(cliente.getNombre())
                                .apellido(cliente.getApellido())
                                .saldoAnterior(tarjeta.getSaldo())
                                .saldoActual(tarjeta.getSaldo())
                        .build());
            } else if (LocalDate.now().isAfter(tarjeta.getFechaVencimiento())) {
                return ResponseEntity.ok(TransaccionCompraResponse.builder()
                        .message("La tarjeta se encuentra vencida")
                        .numeroTarjeta(tarjeta.getNumeroTarjeta())
                        .nombre(cliente.getNombre())
                        .apellido(cliente.getApellido())
                        .saldoAnterior(tarjeta.getSaldo())
                        .saldoActual(tarjeta.getSaldo())
                        .build());
            }
            BigDecimal saldoAnterior = tarjeta.getSaldo();
            tarjeta.setSaldo(tarjeta.getSaldo().subtract(request.getPrice()));
            tarjetaRepository.save(tarjeta);
            Transaccion transaccion = transaccionRepository.save(Transaccion.builder()
                            .idTarjeta(tarjeta.getIdTarjeta())
                            .estadoTransaccion("Realizada")
                            .detalle("Compra")
                            .precioTransaccion(request.getPrice())
                            .fechaTransaccion(LocalDateTime.now())
                    .build());

            return ResponseEntity.ok(TransaccionCompraResponse.builder()
                            .message("Transacción realizada con éxito, id de transaccion: " + transaccion.getIdTransaccion())
                            .numeroTarjeta(tarjeta.getNumeroTarjeta())
                            .nombre(cliente.getNombre())
                            .apellido(cliente.getApellido())
                            .saldoAnterior(saldoAnterior)
                            .compra(request.getPrice())
                            .saldoActual(tarjeta.getSaldo())
                            .fechaTransaccion(transaccion.getFechaTransaccion())
                    .build());

        } catch (Exception e) {
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_003)
                    .description(Constantes.LOG_ERROR_003_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseEntity<ConsultaTransaccionResponse> consultaTransaccion(Integer request) {
        Optional<Transaccion> transaccion = transaccionRepository.findById(request);
        if(!transaccion.isPresent()){
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_004)
                    .description(Constantes.LOG_ERROR_004_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }

        ConsultaTransaccionResponse response = new ConsultaTransaccionResponse();

        response.setMessage("Consulta de transacción numero: " + transaccion.get().getIdTransaccion());
        response.setEstado(transaccion.get().getEstadoTransaccion());
        response.setDetalle(transaccion.get().getDetalle());
        response.setValor(transaccion.get().getPrecioTransaccion());
        response.setFechaTransaccion(transaccion.get().getFechaTransaccion());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AnularTransaccionResponse> anularTransaccion(AnularTransaccionRequest request) {
        Optional<Transaccion> transaccion = transaccionRepository.findById(request.getIdTransaccion());
        LocalDateTime fechaLimite = LocalDateTime.now().minusHours(24);
        if(!transaccion.isPresent()){
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_004)
                    .description(Constantes.LOG_ERROR_004_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();

        } else if (transaccion.get().getEstadoTransaccion().equals("Anulada")) {
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_006)
                    .description(Constantes.LOG_ERROR_006_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }else if (transaccion.get().getFechaTransaccion().isBefore(fechaLimite)) {
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_005)
                    .description(Constantes.LOG_ERROR_005_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }
        try {
            Tarjeta tarjeta = tarjetaRepository.buscarTarjeta(request.getNumeroTarjeta());
            IClienteTarjeta cliente = tarjetaRepository.buscarClienteTarjeta(request.getNumeroTarjeta());

            BigDecimal saldoAnterior = tarjeta.getSaldo();
            transaccion.get().setEstadoTransaccion("Anulada");
            transaccion.get().setFechaTransaccion(LocalDateTime.now());
            tarjeta.setSaldo(tarjeta.getSaldo().add(transaccion.get().getPrecioTransaccion()));

            transaccionRepository.save(transaccion.get());
            tarjetaRepository.save(tarjeta);

            return ResponseEntity.ok(AnularTransaccionResponse.builder()
                            .message("La transaccion numero " + transaccion.get().getIdTransaccion() + " se ha anulado con éxito")
                            .numeroTarjeta(tarjeta.getNumeroTarjeta())
                            .nombre(cliente.getNombre())
                            .apellido(cliente.getApellido())
                            .saldoAnterior(saldoAnterior)
                            .compra(transaccion.get().getPrecioTransaccion())
                            .saldoActual(tarjeta.getSaldo())
                            .fechaTransaccion(LocalDateTime.now())
                    .build());

        } catch (Exception e) {
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_003)
                    .description(Constantes.LOG_ERROR_003_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }
    }
}
