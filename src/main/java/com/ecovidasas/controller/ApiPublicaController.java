package com.ecovidasas.controller;

import com.ecovidasas.service.ApiPublicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
    Controlador encargado de exponer
    los servicios de la API pública.
*/
@RestController
@RequestMapping("/api/publica")
@CrossOrigin(origins = "http://localhost:5173")
public class ApiPublicaController {

    @Autowired
    private ApiPublicaService apiPublicaService;

    /*
        Obtiene la información
        de la API pública.
    */
    @GetMapping("/usuarios")
    public String obtenerUsuarios() {

        return apiPublicaService.obtenerUsuarios();

    }

}