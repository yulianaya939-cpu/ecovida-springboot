package com.ecovidasas.controller;

import com.ecovidasas.entity.Residuo;
import com.ecovidasas.service.ResiduoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
@RequestMapping("/residuos")
public class ResiduoController {

    @Autowired
    private ResiduoService residuoService;

    @GetMapping
    public List<Residuo> listarResiduos() {
        return residuoService.listarResiduos();
    }

    @GetMapping("/{id}")
    public Residuo buscarResiduo(@PathVariable Long id) {
        return residuoService.obtenerResiduoPorId(id);
    }

    @PostMapping
    public Residuo guardarResiduo(@Valid @RequestBody Residuo residuo) {
        return residuoService.guardarResiduo(residuo);
    }

    @PutMapping("/{id}")
    public Residuo actualizarResiduo(
            @PathVariable Long id,
            @Valid @RequestBody Residuo residuo) {

        residuo.setId(id);

        return residuoService.guardarResiduo(residuo);
    }

    @DeleteMapping("/{id}")
    public void eliminarResiduo(@PathVariable Long id) {
        residuoService.eliminarResiduo(id);
    }
}