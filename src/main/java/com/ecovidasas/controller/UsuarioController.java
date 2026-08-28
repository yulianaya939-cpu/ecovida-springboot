package com.ecovidasas.controller;

import com.ecovidasas.entity.Usuario;
import com.ecovidasas.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    // =========================
    // LISTAR USUARIOS
    // =========================

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }


    // =========================
    // BUSCAR USUARIO POR ID
    // =========================

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }


    // =========================
    // CREAR USUARIO
    // SOLO ADMINISTRADOR
    // =========================

    @PostMapping
    @PreAuthorize("hasRole('Administrador')")
    public Usuario guardarUsuario(
            @RequestBody Usuario usuario) {

        return usuarioService.guardarUsuario(usuario);
    }


    // =========================
    // ACTUALIZAR USUARIO
    // SOLO ADMINISTRADOR
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public Usuario actualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        usuario.setId(id);

        return usuarioService.guardarUsuario(usuario);
    }


    // =========================
    // ELIMINAR USUARIO
    // SOLO ADMINISTRADOR
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable Long id) {

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}