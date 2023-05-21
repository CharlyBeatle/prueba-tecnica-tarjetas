package com.prueba.tarjeta.controllerTest;

import com.prueba.tarjeta.controller.TarjetaController;
import com.prueba.tarjeta.dto.tarjetaDtos.*;
import com.prueba.tarjeta.objetosMock.ObjetosMock;
import com.prueba.tarjeta.services.TarjetaService;
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

class TarjetaControllerTest {

    @Mock
    private TarjetaService tarjetaService;

    @InjectMocks
    private TarjetaController controller;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Cuando_GenerarTarjetaEsOK(){
        Integer request = 102030;
        when(tarjetaService.generarTarjeta(request)).thenReturn(ResponseEntity.ok(mock(GenerarTarjetaResponse.class)));

        ResponseEntity<GenerarTarjetaResponse> response = controller.generarTarjeta(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_ActivarTarjetaEsOk(){
        ActivarTarjetaRequest request = ObjetosMock.requestActivar();
        when(tarjetaService.activarTarjeta(request)).thenReturn(ResponseEntity.ok(mock(ActivarTarjetaResponse.class)));

        ResponseEntity<ActivarTarjetaResponse> response = controller.activarTarjeta(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_BloquearTarjetaEsOK(){
        Long request = 2147612847612L;
        when(tarjetaService.bloquearTarjeta(request)).thenReturn(ResponseEntity.ok(mock(BloquearTarjetaResponse.class)));

        ResponseEntity<BloquearTarjetaResponse> response = controller.bloquearTarjeta(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_RecargarSaldoEsOk(){
        RecargaSaldoRequest request = ObjetosMock.requestRecarga();
        when(tarjetaService.recargarSaldo(request)).thenReturn(ResponseEntity.ok(mock(RecargarSaldoResponse.class)));

        ResponseEntity<RecargarSaldoResponse> response = controller.recargaSaldo(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_ConsultarSaldoEsOk(){
        Long request = 45L;
        when(tarjetaService.consultaSaldo(request)).thenReturn(ResponseEntity.ok(mock(ConsultaSaldoResponse.class)));

        ResponseEntity<ConsultaSaldoResponse> response = controller.consultaSaldo(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}
