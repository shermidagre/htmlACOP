package org.example.service;

import org.example.model.Juego;
import org.example.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RecomendacionesService {

    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> obtenerRecomendacionesPorCategoria(String categoria) {

        return  juegoRepository.findByCategoria(categoria);

    }

    public List<Juego> obtenerRecomendacionesPorPrecio(double precio) {

        return  juegoRepository.findByPrecio(precio);
    }
}
