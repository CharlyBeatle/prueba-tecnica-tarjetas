package com.prueba.tarjeta.repositories;

import com.prueba.tarjeta.dto.tarjetaDtos.IClienteTarjeta;
import com.prueba.tarjeta.models.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Integer> {

    @Query("SELECT c FROM Tarjeta c WHERE c.numeroTarjeta = :numeroTarjeta")
    Tarjeta buscarTarjeta(@Param("numeroTarjeta") Long numeroTarjeta);

    @Query(value = "SELECT id_cliente, documento, nombre, apellido FROM clientes c " +
            "INNER JOIN tarjetas_credito t ON c.id_cliente = t.cliente_id " +
            "WHERE t.numero_tarjeta = :numeroTarjeta", nativeQuery = true)
    IClienteTarjeta buscarClienteTarjeta(@Param("numeroTarjeta") Long numeroTarjeta);
}
