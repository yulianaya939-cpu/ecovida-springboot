package com.ecovidasas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // =========================
                // ACCESOS PÚBLICOS
                // =========================

                .requestMatchers("/api/auth/login").permitAll()

                .requestMatchers("/api/publica/**").permitAll()

                .requestMatchers("/api/clima").permitAll()


                // =========================
                // USUARIOS
                // =========================

                // Consultar usuarios:
                // cualquier usuario autenticado
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**")
                .authenticated()

                // Crear usuarios:
                // solamente Administrador
                .requestMatchers(HttpMethod.POST, "/api/usuarios/**")
                .hasRole("Administrador")

                // Modificar usuarios/roles:
                // solamente Administrador
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**")
                .hasRole("Administrador")

                // Eliminar usuarios:
                // solamente Administrador
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**")
                .hasRole("Administrador")


                // =========================
                // CLIENTES
                // =========================

                // Consultar clientes:
                // usuarios autenticados
                .requestMatchers(HttpMethod.GET, "/clientes/**")
                .authenticated()

                // Modificar clientes:
                // solamente Administrador
                .requestMatchers(HttpMethod.POST, "/clientes/**")
                .hasRole("Administrador")

                .requestMatchers(HttpMethod.PUT, "/clientes/**")
                .hasRole("Administrador")

                .requestMatchers(HttpMethod.DELETE, "/clientes/**")
                .hasRole("Administrador")


                // =========================
                // RESIDUOS
                // =========================

                // Consultar residuos:
                // usuarios autenticados
                .requestMatchers(HttpMethod.GET, "/residuos/**")
                .authenticated()

                // Administrar residuos:
                // solamente Administrador
                .requestMatchers(HttpMethod.POST, "/residuos/**")
                .hasRole("Administrador")

                .requestMatchers(HttpMethod.PUT, "/residuos/**")
                .hasRole("Administrador")

                .requestMatchers(HttpMethod.DELETE, "/residuos/**")
                .hasRole("Administrador")


                // =========================
                // RECOLECCIONES
                // =========================

                // Consultar recolecciones:
                // usuarios autenticados
                .requestMatchers(HttpMethod.GET, "/api/recolecciones/**")
                .authenticated()

                // Registrar y actualizar recolecciones:
                // usuarios autenticados
                .requestMatchers(HttpMethod.POST, "/api/recolecciones/**")
                .authenticated()

                .requestMatchers(HttpMethod.PUT, "/api/recolecciones/**")
                .authenticated()

                // Eliminar recolecciones:
                // solamente Administrador
                .requestMatchers(HttpMethod.DELETE, "/api/recolecciones/**")
                .hasRole("Administrador")


                // =========================
                // CUALQUIER OTRA RUTA
                // =========================

                .anyRequest().authenticated()
            )

            // =========================
            // FILTRO JWT
            // =========================

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}