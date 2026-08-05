package com.ecovidasas.service;

import com.ecovidasas.dto.LoginRequest;
import com.ecovidasas.dto.LoginResponse;
import com.ecovidasas.entity.Usuario;
import com.ecovidasas.repository.UsuarioRepository;
import com.ecovidasas.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 Implementación de los servicios
 relacionados con los usuarios.
*/
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    // LISTAR
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // BUSCAR POR ID
    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // GUARDAR
    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // ELIMINAR
    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // LOGIN
    @Override
    public LoginResponse login(LoginRequest request) {

        Optional<Usuario> usuario =
                usuarioRepository.findByCorreo(request.getCorreo());

        if (usuario.isPresent()
                && usuario.get().getPassword().equals(request.getPassword())) {

            String token = jwtService.generarToken(request.getCorreo());

            return new LoginResponse(
                    "Autenticación satisfactoria",
                    true,
                    token
            );
        }

        return new LoginResponse(
                "Error en la autenticación",
                false,
                null
        );
    }

}