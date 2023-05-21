package com.prueba.tarjeta.services;

import com.prueba.tarjeta.dto.tarjetaDtos.*;
import org.springframework.http.ResponseEntity;

public interface TarjetaService {

    ResponseEntity<GenerarTarjetaResponse> generarTarjeta(Integer request);
    ResponseEntity<ActivarTarjetaResponse> activarTarjeta(ActivarTarjetaRequest request);
    ResponseEntity<BloquearTarjetaResponse> bloquearTarjeta(Long request);
    ResponseEntity<RecargarSaldoResponse> recargarSaldo(RecargaSaldoRequest request);
    ResponseEntity<ConsultaSaldoResponse> consultaSaldo(Long request);

}
