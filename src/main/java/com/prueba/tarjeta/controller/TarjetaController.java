package com.prueba.tarjeta.controller;

import com.prueba.tarjeta.dto.tarjetaDtos.*;
import com.prueba.tarjeta.services.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TarjetaController {

    @Autowired
    private TarjetaService tarjetaService;

    @GetMapping("/card/{productId}/number")
    @CrossOrigin(origins = "*")
    public ResponseEntity<GenerarTarjetaResponse> generarTarjeta(@PathVariable("productId") @Valid Integer request){
        return tarjetaService.generarTarjeta(request);
    }

    @PostMapping("/card/enroll")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ActivarTarjetaResponse> activarTarjeta(@RequestBody @Valid ActivarTarjetaRequest request){
        return tarjetaService.activarTarjeta(request);
    }

    @DeleteMapping("/card/{cardId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<BloquearTarjetaResponse> bloquearTarjeta(@PathVariable("cardId") @Valid Long request){
        return tarjetaService.bloquearTarjeta(request);
    }

    @PostMapping("/card/balance")
    @CrossOrigin(origins = "*")
    public ResponseEntity<RecargarSaldoResponse> recargaSaldo(@RequestBody @Valid RecargaSaldoRequest request){
        return tarjetaService.recargarSaldo(request);
    }

    @GetMapping("/card/balance/{cardId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<ConsultaSaldoResponse> consultaSaldo(@PathVariable("cardId") @Valid Long request){
        return tarjetaService.consultaSaldo(request);
    }
}
