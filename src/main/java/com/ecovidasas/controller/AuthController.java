package com.ecovidasas.controller;

import com.ecovidasas.dto.LoginRequest;
import com.ecovidasas.dto.LoginResponse;
import com.ecovidasas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // LOGIN
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return usuarioService.login(request);

    }

}