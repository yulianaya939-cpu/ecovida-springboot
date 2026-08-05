package com.ecovidasas.service;

import com.ecovidasas.dto.LoginRequest;
import com.ecovidasas.dto.LoginResponse;
import com.ecovidasas.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> listarUsuarios();

    Usuario obtenerUsuarioPorId(Long id);

    Usuario guardarUsuario(Usuario usuario);

    void eliminarUsuario(Long id);

    LoginResponse login(LoginRequest request);

}