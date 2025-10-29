// ComprasService.java
package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Compras;
import org.example.repository.ComprasRepository;
import org.springframework.stereotype.Service;
import org.example.model.Juego;
import org.example.model.Usuario;
import org.example.repository.JuegoRepository;
import org.example.repository.UsuarioRepository;
import org.example.dto.Compras.ComprasRequestDTO;

import java.util.List;
import java.util.Optional;

@Service
public class ComprasService {


    private final ComprasRepository comprasRepository;
    private final UsuarioRepository usuarioRepository; // NUEVO
    private final JuegoRepository juegoRepository;


    public ComprasService(ComprasRepository comprasRepository,
                          UsuarioRepository usuarioRepository,
                          JuegoRepository juegoRepository) {
        this.comprasRepository = comprasRepository;
        this.usuarioRepository = usuarioRepository;
        this.juegoRepository = juegoRepository;
    }

    @Transactional
    public Compras crearCompraDesdeDTO(ComprasRequestDTO compraDTO) {

        Usuario usuario = usuarioRepository.findById(compraDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Juego juego = juegoRepository.findById(compraDTO.getJuegoId())
                .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

        Compras nuevaCompra = new Compras(usuario, juego);

        return comprasRepository.save(nuevaCompra);
    }

    @jakarta.transaction.Transactional
    public List<Compras> findAll() {
        return comprasRepository.findAllWithDetails();
    }

    @jakarta.transaction.Transactional
    public Optional<Compras> findById(Long id) {
        return comprasRepository.findByIdWithDetails(id);
    }

    @Transactional
    public void deleteById(Long id) {
        comprasRepository.deleteById(id);
    }

}