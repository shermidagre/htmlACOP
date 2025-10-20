package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.dto.ComprasRequestDTO;
import org.example.model.Compras;
import org.example.service.ComprasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ComprasController.MAPPING)
@CrossOrigin(origins = "*")
public class ComprasController {

    public static final String MAPPING = "/api/compras";

    private final ComprasService comprasService;

    public ComprasController(ComprasService comprasService) {
        this.comprasService = comprasService;
    }

    /**
     * URL: GET /api/compras
     * Devuelve una lista de todas las entidades Compras, incluyendo el Usuario y el Juego referenciados.
     */

    @Operation(summary = "Listar todas las compras")
    @GetMapping
    public ResponseEntity<List<Compras>> comprastotales() {
        List<Compras> compras = comprasService.findAll();
        return ResponseEntity.ok(compras);
    }

    /**
     * URL: GET /api/compras/{id}
     * Devuelve una compra específica por su ID.
     */

    @Operation(summary = "Encontrar compra por id")
    @GetMapping("/{id}")
    public ResponseEntity<Compras> cogercompraporid(@PathVariable Long id) {
        // Llama al servicio que devuelve el Optional<Compras>
        Optional<Compras> compraOptional = comprasService.findById(id);

        // Usamos el Optional para manejar la respuesta:
        return compraOptional
                .map(ResponseEntity::ok) // Si el Optional TIENE valor, devuelve 200 (OK) con el cuerpo.
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si el Optional está VACÍO, devuelve 404 (Not Found).
    }

    /**
     * URL: POST /api/compras
     * Crea una nueva compra. El cuerpo de la solicitud (RequestBody) debe contener
     * el Usuario y el Juego que deben existir previamente.
     */

    @Operation(summary = "Crear una nueva compra")
    @PostMapping
    public ResponseEntity<Compras> crearcompra(@RequestBody ComprasRequestDTO compraDTO) {

        Compras compraGuardada = comprasService.crearCompraDesdeDTO(compraDTO);

        return ResponseEntity.ok(compraGuardada);
    }

    @Operation(summary = "Borrar compra por id")
    @DeleteMapping
    public ResponseEntity<Void> eliminarcompra(@PathVariable Long id) {
        comprasService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
