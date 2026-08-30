package com.ecovidasas.controller;

import com.ecovidasas.entity.Residuo;
import com.ecovidasas.service.ResiduoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/residuos")
public class ResiduoController {

    @Autowired
    private ResiduoService residuoService;


    // =========================================================
    // LISTAR RESIDUOS
    // =========================================================

    @GetMapping
    public List<Residuo> listarResiduos() {
        return residuoService.listarResiduos();
    }


    // =========================================================
    // BUSCAR RESIDUO POR ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Residuo> buscarResiduo(
            @PathVariable Long id) {

        Residuo residuo =
                residuoService.obtenerResiduoPorId(id);

        /*
         * Si el residuo no existe,
         * se devuelve 404 Not Found.
         */
        if (residuo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(residuo);
    }


    // =========================================================
    // REGISTRAR RESIDUO
    // =========================================================

    @PostMapping
    public Residuo guardarResiduo(
            @Valid @RequestBody Residuo residuo) {

        return residuoService.guardarResiduo(residuo);
    }


    // =========================================================
    // ACTUALIZAR RESIDUO
    // =========================================================

    @PutMapping("/{id}")
    public Residuo actualizarResiduo(
            @PathVariable Long id,
            @Valid @RequestBody Residuo residuo) {

        residuo.setId(id);

        return residuoService.guardarResiduo(residuo);
    }


    // =========================================================
    // ELIMINAR RESIDUO
    // =========================================================

    @DeleteMapping("/{id}")
    public void eliminarResiduo(
            @PathVariable Long id) {

        residuoService.eliminarResiduo(id);
    }
}