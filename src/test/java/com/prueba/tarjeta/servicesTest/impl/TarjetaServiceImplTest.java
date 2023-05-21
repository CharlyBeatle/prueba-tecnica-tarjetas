package com.prueba.tarjeta.servicesTest.impl;

import com.prueba.tarjeta.dto.errors.ExceptionDeSistema;
import com.prueba.tarjeta.dto.tarjetaDtos.*;
import com.prueba.tarjeta.models.Tarjeta;
import com.prueba.tarjeta.objetosMock.ObjetosMock;
import com.prueba.tarjeta.repositories.TarjetaRepository;
import com.prueba.tarjeta.services.impl.TarjetaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TarjetaServiceImplTest {

    @Mock
    private TarjetaRepository tarjetaRepository;

    @Mock
    private IClienteTarjeta iClienteTarjeta;

    @InjectMocks
    TarjetaServiceImpl tarjetaService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Cuando_GenerarTarjetaEsOk(){
        Integer request = 123456;
        when(tarjetaRepository.save(Mockito.any(Tarjeta.class))).thenReturn(ObjetosMock.tarjeta());

        ResponseEntity<GenerarTarjetaResponse> response = tarjetaService.generarTarjeta(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Tarjeta generada exitosamente con numero: 1020301729791975", response.getBody().getMessage());
    }

    @Test
    void Cuando_GenerarTarjetaFalla(){
        Integer request = 1;
        assertThrows(ExceptionDeSistema.class, ()-> tarjetaService.generarTarjeta(request));
    }

    @Test
    void Cuando_ActivarTarjetaEsOK_PeroYaEstaActiva(){
        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(ObjetosMock.tarjeta());
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);
        when(tarjetaRepository.save(Mockito.any(Tarjeta.class))).thenReturn(ObjetosMock.tarjeta());

        ResponseEntity<ActivarTarjetaResponse> response = tarjetaService.activarTarjeta(ObjetosMock.requestActivar());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_ActivarTarjetaEsOK(){
        Tarjeta tarjeta = ObjetosMock.tarjeta();
        tarjeta.setEstado("Bloqueado");

        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(tarjeta);
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);

        ResponseEntity<ActivarTarjetaResponse> response = tarjetaService.activarTarjeta(ObjetosMock.requestActivar());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_ActivarTarjetaFalla(){
        ActivarTarjetaRequest request = new ActivarTarjetaRequest();
        assertThrows(ExceptionDeSistema.class, ()-> tarjetaService.activarTarjeta(request));
    }

    @Test
    void Cuando_BloquearTarjetaEsOk(){
        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(ObjetosMock.tarjeta());
        when(tarjetaRepository.save(Mockito.any(Tarjeta.class))).thenReturn(ObjetosMock.tarjeta());

        ResponseEntity<BloquearTarjetaResponse>response = tarjetaService.bloquearTarjeta(Mockito.anyLong());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_BloquearTarjetaEsOK_PeroTarjetaYaEstaBloqueada(){
        Tarjeta tarjeta = ObjetosMock.tarjeta();
        tarjeta.setEstado("Bloqueado");

        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(tarjeta);

        ResponseEntity<BloquearTarjetaResponse>response = tarjetaService.bloquearTarjeta(Mockito.anyLong());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_BloquearTarjetaFalla(){
        Long request = Mockito.anyLong();
        assertThrows(ExceptionDeSistema.class, ()-> tarjetaService.bloquearTarjeta(request));
    }

    @Test
    void Cuando_RecargarSaldoEsOk(){
        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(ObjetosMock.tarjeta());
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);
        when(tarjetaRepository.save(Mockito.any(Tarjeta.class))).thenReturn(ObjetosMock.tarjeta());

        ResponseEntity<RecargarSaldoResponse> response = tarjetaService.recargarSaldo(ObjetosMock.requestRecarga());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_RecargarSaldoFalla_TarjetaBloqueada(){
        Tarjeta tarjeta = ObjetosMock.tarjeta();
        tarjeta.setEstado("Bloqueado");

        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(tarjeta);
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);

        ResponseEntity<RecargarSaldoResponse> response = tarjetaService.recargarSaldo(ObjetosMock.requestRecarga());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_RecargarSaldoFalla(){
        RecargaSaldoRequest request = new RecargaSaldoRequest();
        assertThrows(ExceptionDeSistema.class, ()-> tarjetaService.recargarSaldo(request));
    }

    @Test
    void Cuando_ConsultarSaldoEsOk(){
        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(ObjetosMock.tarjeta());
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);

        ResponseEntity<ConsultaSaldoResponse> response = tarjetaService.consultaSaldo(Mockito.anyLong());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_ConsultaSaldoFalla_TarjetaBloqueada(){
        Tarjeta tarjeta = ObjetosMock.tarjeta();
        tarjeta.setEstado("Bloqueado");

        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(tarjeta);
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);

        ResponseEntity<ConsultaSaldoResponse> response = tarjetaService.consultaSaldo(Mockito.anyLong());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_ConsultaSaldoFalla(){
        Long request = Mockito.anyLong();
        assertThrows(ExceptionDeSistema.class, ()-> tarjetaService.consultaSaldo(request));
    }
}
