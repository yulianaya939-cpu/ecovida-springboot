/*
 * SecurityConfig.java
 *
 * Configuración principal de seguridad
 * del sistema Eco Vida.
 *
 * Aquí se configuran:
 *
 * - Autenticación mediante JWT.
 * - Autorización según roles.
 * - CORS entre React y Spring Boot.
 * - Sesiones sin estado.
 * - Codificación BCrypt.
 * - Permisos para cada módulo.
 */

package com.ecovidasas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    /*
     * Constructor encargado de recibir
     * el filtro de autenticación JWT.
     */
    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;

    }


    /*
     * Codificador de contraseñas.
     *
     * BCrypt permite almacenar las contraseñas
     * utilizando un hash seguro.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }


    /*
     * Configuración de CORS.
     *
     * Permite que el frontend React,
     * ejecutándose en localhost:5173,
     * pueda comunicarse con Spring Boot
     * en localhost:8080.
     *
     * También permite enviar el encabezado
     * Authorization donde viaja el JWT.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();


        /*
         * Origen permitido.
         */
        configuration.setAllowedOrigins(
                Arrays.asList(
                        "http://localhost:5173"
                )
        );


        /*
         * Métodos HTTP utilizados
         * por la aplicación Eco Vida.
         */
        configuration.setAllowedMethods(
                Arrays.asList(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );


        /*
         * Encabezados permitidos.
         *
         * Authorization es necesario
         * para enviar el token JWT.
         */
        configuration.setAllowedHeaders(
                Arrays.asList(
                        "Authorization",
                        "Content-Type"
                )
        );


        /*
         * Permite las solicitudes configuradas.
         */
        configuration.setAllowCredentials(true);


        /*
         * Aplicamos la configuración CORS
         * a todas las rutas del backend.
         */
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }


    /*
     * Configuración principal
     * de Spring Security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

            /*
             * Activa la configuración CORS.
             */
            .cors(cors ->
                cors.configurationSource(
                    corsConfigurationSource()
                )
            )


            /*
             * CSRF se deshabilita porque la API
             * utiliza autenticación mediante JWT.
             */
            .csrf(csrf ->
                csrf.disable()
            )


            /*
             * La aplicación no mantiene sesiones
             * tradicionales en el servidor.
             */
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )


            /*
             * Configuración de permisos
             * según las rutas y los roles.
             */
            .authorizeHttpRequests(auth -> auth


                // =========================================
                // SOLICITUDES CORS
                // =========================================

                /*
                 * El navegador utiliza OPTIONS
                 * antes de algunas solicitudes.
                 */
                .requestMatchers(
                        HttpMethod.OPTIONS,
                        "/**"
                )
                .permitAll()


                // =========================================
                // MANEJO DE ERRORES
                // =========================================

                /*
                 * Permite que Spring Boot procese
                 * correctamente las respuestas de error
                 * sin convertir un 400/404/500 en un 403.
                 */
                .requestMatchers(
                        "/error"
                )
                .permitAll()


                // =========================================
                // ACCESOS PÚBLICOS
                // =========================================

                /*
                 * El login debe ser público porque
                 * aquí se obtiene inicialmente el JWT.
                 */
                .requestMatchers(
                        "/api/auth/login"
                )
                .permitAll()


                /*
                 * Servicios públicos externos.
                 */
                .requestMatchers(
                        "/api/publica/**"
                )
                .permitAll()


                /*
                 * Servicio de clima.
                 */
                .requestMatchers(
                        "/api/clima"
                )
                .permitAll()


                // =========================================
                // USUARIOS
                // =========================================

                /*
                 * La gestión de usuarios pertenece
                 * exclusivamente al Administrador.
                 */

                .requestMatchers(
                        HttpMethod.GET,
                        "/api/usuarios/**"
                )
                .hasRole("Administrador")


                .requestMatchers(
                        HttpMethod.POST,
                        "/api/usuarios/**"
                )
                .hasRole("Administrador")


                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/usuarios/**"
                )
                .hasRole("Administrador")


                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/usuarios/**"
                )
                .hasRole("Administrador")


                // =========================================
                // CLIENTES
                // =========================================

                /*
                 * Administrador y Usuario pueden
                 * consultar clientes.
                 */
                .requestMatchers(
                        HttpMethod.GET,
                        "/clientes/**"
                )
                .authenticated()


                /*
                 * Administrador y Usuario pueden
                 * registrar clientes.
                 */
                .requestMatchers(
                        HttpMethod.POST,
                        "/clientes/**"
                )
                .authenticated()


                /*
                 * Administrador y Usuario pueden
                 * modificar clientes.
                 */
                .requestMatchers(
                        HttpMethod.PUT,
                        "/clientes/**"
                )
                .authenticated()


                /*
                 * Solamente el Administrador
                 * puede eliminar clientes.
                 */
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/clientes/**"
                )
                .hasRole("Administrador")


                // =========================================
                // RESIDUOS
                // =========================================

                /*
                 * Administrador y Usuario pueden
                 * consultar residuos.
                 */
                .requestMatchers(
                        HttpMethod.GET,
                        "/residuos/**"
                )
                .authenticated()


                /*
                 * Administrador y Usuario pueden
                 * registrar residuos.
                 */
                .requestMatchers(
                        HttpMethod.POST,
                        "/residuos/**"
                )
                .authenticated()


                /*
                 * Administrador y Usuario pueden
                 * modificar residuos.
                 */
                .requestMatchers(
                        HttpMethod.PUT,
                        "/residuos/**"
                )
                .authenticated()


                /*
                 * Solamente el Administrador
                 * puede eliminar residuos.
                 */
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/residuos/**"
                )
                .hasRole("Administrador")


                // =========================================
                // RECOLECCIONES
                // =========================================

                /*
                 * Administrador y Usuario pueden
                 * consultar recolecciones.
                 */
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/recolecciones/**"
                )
                .authenticated()


                /*
                 * Administrador y Usuario pueden
                 * registrar recolecciones.
                 */
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/recolecciones/**"
                )
                .authenticated()


                /*
                 * Administrador y Usuario pueden
                 * modificar recolecciones.
                 */
                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/recolecciones/**"
                )
                .authenticated()


                /*
                 * Solamente el Administrador
                 * puede eliminar recolecciones.
                 */
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/recolecciones/**"
                )
                .hasRole("Administrador")


                // =========================================
                // OTRAS RUTAS
                // =========================================

                /*
                 * Cualquier otra ruta requiere
                 * que el usuario esté autenticado.
                 */
                .anyRequest()
                .authenticated()

            )


            /*
             * El filtro JWT se ejecuta antes
             * del filtro estándar de autenticación.
             */
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );


        return http.build();
    }
}