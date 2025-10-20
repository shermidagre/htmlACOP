package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.model.Juego;
import org.example.repository.JuegoRepository;
import org.example.service.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(JuegoController.MAPPING)
@CrossOrigin(origins = "*")
public class JuegoController {

    public static final String MAPPING = "/api";


    @Autowired
    private JuegoRepository juegoRepository;
    @Autowired
    private JuegoService juegoService;


    @Operation(summary = "Crear un nuevo juego")
    @PostMapping("/juegos")
    public Juego crearJuego(@RequestBody Juego juego) {
        return juegoService.crearOuActualizarJuego(juego);
    }

    @Operation(summary = "Obtener todos los juegos")
    @GetMapping("/juegos")
    public List<Juego> obtenerJuegos() {
        return juegoService.obtenerJuegos();
    }

    @Operation(summary = "Obtener un juego por ID")
    @GetMapping("/juegos/{id}")
    public Optional<Juego> buscarjuegoporid(@PathVariable Long id){

            return juegoService.buscarjuegoporid(id);

    }

    @Operation(summary = "Eliminar un juego por ID")
    @DeleteMapping("/juegos/{id}")
    public Juego borrarjuegoporid(@PathVariable Long id){

       return juegoService.borrarjuegoporid(id);

    }

}

