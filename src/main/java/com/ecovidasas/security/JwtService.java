package com.ecovidasas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/*
 * Servicio encargado de generar y validar
 * los tokens JWT utilizados para la autenticación
 * de los usuarios del sistema EcoVida.
 */
@Service
public class JwtService {

    /*
     * La clave secreta ya no se escribe directamente
     * en el código fuente.
     *
     * El valor se obtiene desde una variable de entorno
     * mediante la configuración de application.properties.
     */
    @Value("${jwt.secret}")
    private String secretKey;

    /*
     * Tiempo de duración del token.
     *
     * Actualmente corresponde a 1 hora.
     */
    private static final long EXPIRATION_TIME =
            1000 * 60 * 60;

    /*
     * Genera la clave utilizada para firmar
     * y validar los tokens JWT.
     */
    private Key obtenerClave() {

        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    /*
     * Genera un token JWT para un usuario autenticado.
     *
     * El correo identifica al usuario y el rol se almacena
     * dentro del token para posteriormente determinar
     * sus permisos.
     */
    public String generarToken(String correo, String rol) {

        return Jwts.builder()
                .setSubject(correo)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME
                        )
                )
                .signWith(
                        obtenerClave(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    /*
     * Valida el token JWT recibido.
     *
     * Si el token es válido, devuelve sus datos.
     * Si es inválido o está vencido, JJWT genera
     * una excepción que será manejada por el filtro JWT.
     */
    public Claims validarToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(obtenerClave())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}