package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.model.Juego;
import org.example.service.RecomendacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.service.JuegoService;

import java.util.List;




@RestController
@RequestMapping(RecomendacionesController.MAPPING)
@CrossOrigin(origins = "*")
public class RecomendacionesController {

    public static final String MAPPING = "/api";

    @Autowired
    private RecomendacionesService recomendacionesService;
    @Autowired
    private JuegoService juegoService;
    @Autowired
    private JuegoController juegoController;

    @Operation(summary = "Obtener recomendaciones por Categoria")
    @GetMapping("/recomendaciones/categoria/{categoria}")
    public List<Juego> obtenerRecomendacionesPorCategoria(@PathVariable String categoria) {

        return recomendacionesService.obtenerRecomendacionesPorCategoria(categoria);
    }

    @Operation(summary = "Obtener recomendaciones por Precio")
    @GetMapping("/recomendaciones/precio/{precio}")
    public List<Juego> obtenerRecomendacionesPorPrecio(@PathVariable double precio) {

        return recomendacionesService.obtenerRecomendacionesPorPrecio(precio);
    }


}
