package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.example.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(UsuarioController.MAPPING)
@CrossOrigin(origins = "*")
public class UsuarioController {

    public static final String MAPPING = "/api";

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;


    @Operation(summary = "Crear un novo usuario")
    @PostMapping("/usuarios")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearOuActualizarUsuario(usuario);
    }

    @Operation(summary = "Obter todos os usuarios")
    @GetMapping("/usuarios")
    public List<Usuario> obterUsuarios() {
        return usuarioService.obterUsuarios();
    }

    @Operation(summary = "Obtener usuario por id")
    @GetMapping("/usuarios/{id}")
    public Optional<Usuario> buscarusuarioporid(@PathVariable Long id){
            return usuarioService.buscarusuarioporid(id);
    }

    @Operation(summary = "Eliminar usuario por id")
    @DeleteMapping("/usuarios/{id}")

    public Usuario borrarusuarioporid(@PathVariable Long id){
        return usuarioService.borrarusuarioporid(id);
    }
}
