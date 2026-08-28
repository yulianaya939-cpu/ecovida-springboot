package com.ecovidasas.service;

import com.ecovidasas.dto.LoginRequest;
import com.ecovidasas.dto.LoginResponse;
import com.ecovidasas.entity.Usuario;
import com.ecovidasas.repository.UsuarioRepository;
import com.ecovidasas.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // =========================
    // LISTAR
    // =========================

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }


    // =========================
    // BUSCAR POR ID
    // =========================

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {

        return usuarioRepository
                .findById(id)
                .orElse(null);
    }


    // =========================
    // GUARDAR / ACTUALIZAR
    // =========================

    @Override
    public Usuario guardarUsuario(Usuario usuario) {

        // Validar rol
        if (!"Administrador".equals(usuario.getRol())
                && !"Usuario".equals(usuario.getRol())) {

            throw new IllegalArgumentException(
                    "El rol debe ser Administrador o Usuario"
            );
        }

        /*
         * Si la contraseña es nueva,
         * se almacena utilizando BCrypt.
         */
        if (usuario.getPassword() != null
                && !usuario.getPassword().isBlank()
                && !usuario.getPassword().startsWith("$2a$")
                && !usuario.getPassword().startsWith("$2b$")
                && !usuario.getPassword().startsWith("$2y$")) {

            usuario.setPassword(
                    passwordEncoder.encode(
                            usuario.getPassword()
                    )
            );
        }

        return usuarioRepository.save(usuario);
    }


    // =========================
    // ELIMINAR
    // =========================

    @Override
    public void eliminarUsuario(Long id) {

        usuarioRepository.deleteById(id);
    }


    // =========================
    // LOGIN
    // =========================

    @Override
    public LoginResponse login(LoginRequest request) {

        Optional<Usuario> usuarioOptional =
                usuarioRepository.findByCorreo(
                        request.getCorreo()
                );

        if (usuarioOptional.isPresent()) {

            Usuario usuario =
                    usuarioOptional.get();

            boolean passwordCorrecta =
                    passwordEncoder.matches(
                            request.getPassword(),
                            usuario.getPassword()
                    );

            boolean usuarioActivo =
                    Boolean.TRUE.equals(
                            usuario.getActivo()
                    );

            if (passwordCorrecta && usuarioActivo) {

                String token =
                        jwtService.generarToken(
                                usuario.getCorreo(),
                                usuario.getRol()
                        );

                return new LoginResponse(
                        "Autenticación satisfactoria",
                        true,
                        token,
                        usuario.getRol()
                );
            }
        }

        return new LoginResponse(
                "Error en la autenticación",
                false,
                null,
                null
        );
    }
}