package com.prueba.tarjeta.services.impl;

import com.prueba.tarjeta.dto.errors.ExceptionDeSistema;
import com.prueba.tarjeta.dto.tarjetaDtos.*;
import com.prueba.tarjeta.models.Tarjeta;
import com.prueba.tarjeta.repositories.TarjetaRepository;
import com.prueba.tarjeta.services.TarjetaService;
import com.prueba.tarjeta.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    private static final Random RANDOM = new Random();

    @Override
    public ResponseEntity<GenerarTarjetaResponse> generarTarjeta(Integer request) {
        if (request.toString().length() != 6){
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_001)
                    .description(Constantes.LOG_ERROR_001_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }
        Tarjeta tarjeta = tarjetaRepository.save(Tarjeta.builder()
                        .numeroTarjeta(generarNumeroTarjeta(request))
                        .fechaCreacion(LocalDate.now())
                        .fechaActivacion(null)
                        .fechaVencimiento(null)
                        .saldo(BigDecimal.valueOf(0))
                        .estado("Bloqueado")
                .build());

        return ResponseEntity.ok(GenerarTarjetaResponse.builder().message("Tarjeta generada exitosamente con numero: " + tarjeta.getNumeroTarjeta()).build());
    }

    @Override
    public ResponseEntity<ActivarTarjetaResponse> activarTarjeta(ActivarTarjetaRequest request) {
        try {
            Tarjeta tarjeta = tarjetaRepository.buscarTarjeta(request.getNumeroTarjeta());
            IClienteTarjeta cliente = tarjetaRepository.buscarClienteTarjeta(request.getNumeroTarjeta());

            if(tarjeta.getEstado().equals(Constantes.ACTIVO)){
                return ResponseEntity.ok(ActivarTarjetaResponse.builder()
                        .message("La tarjeta que desea activar ya se encuentra activa")
                        .numeroTarjeta(tarjeta.getNumeroTarjeta())
                        .nombreCliente(cliente.getNombre())
                        .apellidoCliente(cliente.getApellido())
                        .fechaActivacion(tarjeta.getFechaActivacion())
                        .fechaVencimiento(tarjeta.getFechaVencimiento())
                        .build());
            } else {
                tarjeta.setEstado(Constantes.ACTIVO);
                tarjeta.setIdCliente(1);
                tarjeta.setFechaActivacion(LocalDate.now());
                tarjeta.setFechaVencimiento(LocalDate.now().plusYears(3));
                tarjetaRepository.save(tarjeta);
                cliente = tarjetaRepository.buscarClienteTarjeta(request.getNumeroTarjeta());
                return ResponseEntity.ok(ActivarTarjetaResponse.builder()
                        .message("La tarjeta ha sido activada correctamente")
                        .numeroTarjeta(tarjeta.getNumeroTarjeta())
                        .nombreCliente(cliente.getNombre())
                        .apellidoCliente(cliente.getApellido())
                        .fechaActivacion(tarjeta.getFechaActivacion())
                        .fechaVencimiento(tarjeta.getFechaVencimiento())
                        .build());
            }
        } catch (Exception e) {
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_002)
                    .description(Constantes.LOG_ERROR_002_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseEntity<BloquearTarjetaResponse> bloquearTarjeta(Long request) {
        try {
            Tarjeta tarjeta = tarjetaRepository.buscarTarjeta(request);

            if(tarjeta.getEstado().equals(Constantes.ACTIVO)){
                tarjeta.setEstado("Bloqueado");
                tarjetaRepository.save(tarjeta);
                return ResponseEntity.ok(BloquearTarjetaResponse.builder()
                        .message("La tarjeta numero " + tarjeta.getNumeroTarjeta() + " ha sido bloqueada")
                        .build());
            } else {
                return ResponseEntity.ok(BloquearTarjetaResponse.builder()
                        .message("La tarjeta numero " + tarjeta.getNumeroTarjeta() +" ya se encuentra bloqueada")
                        .build());
            }
        } catch (Exception e) {
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_002)
                    .description(Constantes.LOG_ERROR_002_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseEntity<RecargarSaldoResponse> recargarSaldo(RecargaSaldoRequest request) {
        try {
            Tarjeta tarjeta = tarjetaRepository.buscarTarjeta(request.getNumeroTarjeta());
            IClienteTarjeta cliente = tarjetaRepository.buscarClienteTarjeta(request.getNumeroTarjeta());

            if (tarjeta.getEstado().equals("Activo")){
                BigDecimal saldoAnterior = tarjeta.getSaldo();
                tarjeta.setSaldo(tarjeta.getSaldo().add(request.getBalance()));
                tarjetaRepository.save(tarjeta);

                return ResponseEntity.ok(RecargarSaldoResponse.builder()
                        .message("Se ha cargado el saldo correctamente")
                        .numeroTarjeta(tarjeta.getNumeroTarjeta())
                        .nombreCliente(cliente.getNombre())
                        .apellidoCliente(cliente.getApellido())
                        .saldoAnterior(saldoAnterior)
                        .saldoActual(tarjeta.getSaldo())
                        .build());
            }
            return ResponseEntity.ok(RecargarSaldoResponse.builder()
                    .message("Esta tarjeta se encuentra bloqueada")
                    .build());

        } catch (Exception e) {
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_002)
                    .description(Constantes.LOG_ERROR_002_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseEntity<ConsultaSaldoResponse> consultaSaldo(Long request) {
        try {
            Tarjeta tarjeta = tarjetaRepository.buscarTarjeta(request);
            IClienteTarjeta cliente = tarjetaRepository.buscarClienteTarjeta(request);

            if (tarjeta.getEstado().equals("Activo")) {
                return ResponseEntity.ok(ConsultaSaldoResponse.builder()
                        .message("Consulta de saldo")
                        .numeroTarjeta(tarjeta.getNumeroTarjeta())
                        .nombreCliente(cliente.getNombre())
                        .apellidoCliente(cliente.getApellido())
                        .saldo(tarjeta.getSaldo())
                        .build());
            }
            return ResponseEntity.ok(ConsultaSaldoResponse.builder()
                    .message("Esta tarjeta se encuentra bloqueada")
                    .build());

        } catch (Exception e) {
            throw ExceptionDeSistema.builder()
                    .code(Constantes.LOG_ERROR_002)
                    .description(Constantes.LOG_ERROR_002_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constantes.VALIDATION_ERROR)
                    .build();
        }
    }

    private Long generarNumeroTarjeta(Integer request){
        Integer numeroAleatorio = RANDOM.nextInt(900000000) + 1000000000;
        String resultado = String.valueOf(request).concat(String.valueOf(numeroAleatorio));
        return Long.valueOf(resultado);
    }

}
