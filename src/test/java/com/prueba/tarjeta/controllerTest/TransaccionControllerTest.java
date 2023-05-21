package com.prueba.tarjeta.controllerTest;

import com.prueba.tarjeta.controller.TransaccionController;
import com.prueba.tarjeta.dto.tarjetaDtos.GenerarTarjetaResponse;
import com.prueba.tarjeta.dto.transaccionDtos.*;
import com.prueba.tarjeta.objetosMock.ObjetosMock;
import com.prueba.tarjeta.services.TransaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransaccionControllerTest {

    @Mock
    private TransaccionService transaccionService;

    @InjectMocks
    TransaccionController controller;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Cuando_TransaccionCompraEsOK() {
        TransaccionCompraRequest request = ObjetosMock.requestCompra();
        when(transaccionService.compra(request)).thenReturn(ResponseEntity.ok(mock(TransaccionCompraResponse.class)));

        ResponseEntity<TransaccionCompraResponse> response = controller.compra(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_ConsultarTransaccionEsOK() {
        Integer request = 1;
        when(transaccionService.consultaTransaccion(request)).thenReturn(ResponseEntity.ok(mock(ConsultaTransaccionResponse.class)));

        ResponseEntity<ConsultaTransaccionResponse> response = controller.consultaTransaccion(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_AnularTransaccionEsOK() {
        AnularTransaccionRequest request = ObjetosMock.requestAnular();
        when(transaccionService.anularTransaccion(request)).thenReturn(ResponseEntity.ok(mock(AnularTransaccionResponse.class)));

        ResponseEntity<AnularTransaccionResponse> response = controller.anularTransaccion(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}
