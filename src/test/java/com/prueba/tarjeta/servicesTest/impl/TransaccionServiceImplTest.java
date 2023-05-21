package com.prueba.tarjeta.servicesTest.impl;

import com.prueba.tarjeta.dto.errors.ExceptionDeSistema;
import com.prueba.tarjeta.dto.tarjetaDtos.IClienteTarjeta;
import com.prueba.tarjeta.dto.transaccionDtos.*;
import com.prueba.tarjeta.models.Tarjeta;
import com.prueba.tarjeta.models.Transaccion;
import com.prueba.tarjeta.objetosMock.ObjetosMock;
import com.prueba.tarjeta.repositories.TarjetaRepository;
import com.prueba.tarjeta.repositories.TransaccionRepository;
import com.prueba.tarjeta.services.impl.TransaccionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransaccionServiceImplTest {

    @Mock
    private TarjetaRepository tarjetaRepository;

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private IClienteTarjeta iClienteTarjeta;

    @InjectMocks
    TransaccionServiceImpl transaccionService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Cuando_TransaccionCompraEsOK_Entonces_RetornaObjeto_TransaccionCompraResponse() {

        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(ObjetosMock.tarjeta());
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);
        when(tarjetaRepository.save(Mockito.any(Tarjeta.class))).thenReturn(ObjetosMock.tarjeta());
        when(transaccionRepository.save(Mockito.any(Transaccion.class))).thenReturn(ObjetosMock.transaccion());

        ResponseEntity<TransaccionCompraResponse> response = transaccionService.compra(ObjetosMock.requestCompra());

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Transacción realizada con éxito, id de transaccion: 5", response.getBody().getMessage());
    }

    @Test
    void Cuando_SaldoEsInsuficiente_Entonces_RetornaObjeto_ConMensaje(){

        Tarjeta tarjeta = ObjetosMock.tarjeta();
        tarjeta.setSaldo(BigDecimal.valueOf(0));

        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(tarjeta);
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);
        ResponseEntity<TransaccionCompraResponse> response = transaccionService.compra(ObjetosMock.requestCompra());

        assertEquals("El saldo en la tarjeta es insuficiente para hacer la compra", response.getBody().getMessage());

    }

    @Test
    void Cuando_FechaVencia_Entonces_RetornaObjeto_ConMensaje(){

        Tarjeta tarjeta = ObjetosMock.tarjeta();
        LocalDate fechaVencida = LocalDate.now().minusYears(1);
        tarjeta.setFechaVencimiento(fechaVencida);

        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(tarjeta);
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);
        ResponseEntity<TransaccionCompraResponse> response = transaccionService.compra(ObjetosMock.requestCompra());

        assertEquals("La tarjeta se encuentra vencida", response.getBody().getMessage());

    }

    @Test
    void Cuando_TarjetaNoExiste_Entonces_RetornaExcepcion(){
        TransaccionCompraRequest request = new TransaccionCompraRequest();
        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenThrow(ExceptionDeSistema.class);

        Exception exception = assertThrows(ExceptionDeSistema.class, () -> {
            transaccionService.compra(request);
        });

        assertNull(exception.getMessage());
    }

    @Test
    void Cuando_ConsultaTransaccionEsOK(){
        when(transaccionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(ObjetosMock.transaccion()));

        ResponseEntity<ConsultaTransaccionResponse> response = transaccionService.consultaTransaccion(Mockito.anyInt());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_ConsultarTransaccionFalla(){
        Integer request = Mockito.anyInt();
        assertThrows(ExceptionDeSistema.class, ()-> transaccionService.consultaTransaccion(request));
    }

    @Test
    void Cuando_AnularTransaccionEsOK(){
        when(transaccionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(ObjetosMock.transaccion()));
        when(tarjetaRepository.buscarTarjeta(Mockito.anyLong())).thenReturn(ObjetosMock.tarjeta());
        when(tarjetaRepository.buscarClienteTarjeta(Mockito.anyLong())).thenReturn(iClienteTarjeta);

        when(tarjetaRepository.save(Mockito.any(Tarjeta.class))).thenReturn(ObjetosMock.tarjeta());
        when(transaccionRepository.save(Mockito.any(Transaccion.class))).thenReturn(ObjetosMock.transaccion());

        ResponseEntity<AnularTransaccionResponse> response = transaccionService.anularTransaccion(ObjetosMock.requestAnular());

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void Cuando_AnularTransaccionFalla_TransaccionNoExiste(){
        AnularTransaccionRequest request = new AnularTransaccionRequest();
        assertThrows(ExceptionDeSistema.class, ()-> transaccionService.anularTransaccion(request));
    }

    @Test
    void Cuando_AnularTransaccionFalla_TransaccionAnulada(){
        Transaccion transaccion = ObjetosMock.transaccion();
        transaccion.setEstadoTransaccion("Anulada");
        when(transaccionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(transaccion));

        assertThrows(ExceptionDeSistema.class, ()-> transaccionService.anularTransaccion(ObjetosMock.requestAnular()));
    }

    @Test
    void Cuando_AnularTransaccionFalla_FechaVencida(){
        Transaccion transaccion = ObjetosMock.transaccion();
        transaccion.setFechaTransaccion(LocalDateTime.now().minusYears(1));
        when(transaccionRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(transaccion));

        assertThrows(ExceptionDeSistema.class, ()-> transaccionService.anularTransaccion(ObjetosMock.requestAnular()));
    }
}
