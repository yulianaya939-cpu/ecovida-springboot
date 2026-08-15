package com.ecovidasas.controller;

import com.ecovidasas.service.ClimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
    Controlador encargado de exponer
    el servicio de información climática
    para apoyar la planificación de
    las recolecciones de Eco Vida.
*/
@RestController
@RequestMapping("/api/clima")
@CrossOrigin(origins = "http://localhost:5173")
public class ClimaController {

    @Autowired
    private ClimaService climaService;

    /*
        Consulta información climática
        de Cartagena mediante una API
        pública externa.
    */
    @GetMapping
    public String obtenerClima() {

        return climaService.obtenerClima();
    }
}