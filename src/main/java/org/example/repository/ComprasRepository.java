package org.example.repository;

import org.example.model.Compras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComprasRepository extends JpaRepository<Compras, Long> {

    /**
     * Usa FETCH JOIN para cargar Usuario y Juego inmediatamente con la Compra.
     * Esto resuelve el error de serialización (ByteBuddyInterceptor).
     */
    @Query("SELECT c FROM Compras c JOIN FETCH c.usuario u JOIN FETCH c.juego j WHERE c.id = :id")
    Optional<Compras> findByIdWithDetails(@Param("id") Long id);


    /**
     * Usa FETCH JOIN para cargar todas las Compras y sus relaciones (Usuario, Juego)
     * en una sola consulta, resolviendo el error de serialización.
     */
    @Query("SELECT c FROM Compras c JOIN FETCH c.usuario u JOIN FETCH c.juego j")
    List<Compras> findAllWithDetails();
}