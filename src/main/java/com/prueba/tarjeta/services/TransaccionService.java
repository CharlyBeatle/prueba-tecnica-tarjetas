package com.prueba.tarjeta.services;

import com.prueba.tarjeta.dto.transaccionDtos.*;
import org.springframework.http.ResponseEntity;

public interface TransaccionService {

    ResponseEntity<TransaccionCompraResponse> compra(TransaccionCompraRequest request);
    ResponseEntity<ConsultaTransaccionResponse> consultaTransaccion(Integer request);
    ResponseEntity<AnularTransaccionResponse> anularTransaccion(AnularTransaccionRequest request);
}
