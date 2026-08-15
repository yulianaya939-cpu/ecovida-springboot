package com.ecovidasas.controller;

import com.ecovidasas.entity.Recoleccion;
import com.ecovidasas.service.RecoleccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Controlador REST encargado de exponer
    los servicios web para gestionar
    las recolecciones de Eco Vida.
*/
@RestController
@RequestMapping("/api/recolecciones")
@CrossOrigin(origins = "http://localhost:5173")
public class RecoleccionController {

    @Autowired
    private RecoleccionService recoleccionService;

    /*
        Consulta todas las recolecciones
        registradas en el sistema.
    */
    @GetMapping
    public ResponseEntity<List<Recoleccion>> listarTodas() {

        return ResponseEntity.ok(
                recoleccionService.listarTodas()
        );
    }

    /*
        Consulta una recolección
        utilizando su identificador.
    */
    @GetMapping("/{id}")
    public ResponseEntity<Recoleccion> buscarPorId(
            @PathVariable Long id) {

        Recoleccion recoleccion =
                recoleccionService.buscarPorId(id);

        if (recoleccion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(recoleccion);
    }

    /*
        Registra una nueva recolección.
    */
    @PostMapping
    public ResponseEntity<Recoleccion> guardar(
            @RequestBody Recoleccion recoleccion) {

        Recoleccion nuevaRecoleccion =
                recoleccionService.guardar(recoleccion);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevaRecoleccion);
    }

    /*
        Actualiza una recolección existente.
    */
    @PutMapping("/{id}")
    public ResponseEntity<Recoleccion> actualizar(
            @PathVariable Long id,
            @RequestBody Recoleccion recoleccion) {

        Recoleccion existente =
                recoleccionService.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setFecha(recoleccion.getFecha());
        existente.setDepartamento(recoleccion.getDepartamento());
        existente.setMunicipio(recoleccion.getMunicipio());
        existente.setEstado(recoleccion.getEstado());
        existente.setCliente(recoleccion.getCliente());
        existente.setResiduo(recoleccion.getResiduo());

        Recoleccion actualizada =
                recoleccionService.guardar(existente);

        return ResponseEntity.ok(actualizada);
    }

    /*
        Elimina una recolección
        utilizando su identificador.
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        Recoleccion existente =
                recoleccionService.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        recoleccionService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}