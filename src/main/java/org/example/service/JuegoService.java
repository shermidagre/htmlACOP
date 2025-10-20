package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Juego;
import org.example.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {

    private static JuegoRepository juegoRepository;

    @Autowired
    public JuegoService(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }
    @Transactional
    public Juego crearOuActualizarJuego(Juego juego) {
        return juegoRepository.save(juego);
    }

    public List<Juego> obtenerJuegos() {
        return juegoRepository.findAll();
    }

    public static Optional<Juego> buscarjuegoporid(Long id) {
        return juegoRepository.findById(id);
    }

    public Juego borrarjuegoporid(Long id) {
        Juego juego = juegoRepository.findById(id).orElse(null);
        if (juego != null) {
            juegoRepository.delete(juego);
        }
        return juego;
    }
}