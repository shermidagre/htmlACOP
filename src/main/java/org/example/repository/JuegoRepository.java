package org.example.repository;

import org.example.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// En tu interfaz JuegoRepository
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    List<Juego> findByCategoria(String categoria);

    List<Juego> findByPrecio(double precio);

}

