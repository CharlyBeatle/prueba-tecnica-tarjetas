package com.prueba.tarjeta.controller;

import com.prueba.tarjeta.dto.transaccionDtos.*;
import com.prueba.tarjeta.services.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransaccionController {

    @Autowired
    TransaccionService transaccionService;

    @PostMapping("/transaction/purchase")
    public ResponseEntity<TransaccionCompraResponse> compra(@RequestBody @Valid TransaccionCompraRequest request){
        return transaccionService.compra(request);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<ConsultaTransaccionResponse> consultaTransaccion(@PathVariable("transactionId") @Valid Integer request){
        return transaccionService.consultaTransaccion(request);
    }

    @PostMapping("/transaction/anulation")
    public ResponseEntity<AnularTransaccionResponse> anularTransaccion(@RequestBody @Valid AnularTransaccionRequest request){
        return transaccionService.anularTransaccion(request);
    }
}
