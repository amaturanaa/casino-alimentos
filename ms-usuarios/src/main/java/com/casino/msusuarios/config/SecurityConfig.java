package com.casino.msusuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Configuración de seguridad del microservicio ms-usuarios
// @Configuration indica que esta clase contiene definiciones de beans de Spring
// @EnableWebSecurity activa la configuración de seguridad web de Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bean de encriptación de contraseñas usando el algoritmo BCrypt
    // BCrypt es un algoritmo de hashing adaptativo seguro para contraseñas
    // Se inyecta en UsuarioServiceImpl para encriptar antes de persistir
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura la cadena de filtros de seguridad HTTP
    // Define qué rutas son accesibles y cómo se maneja la sesión
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilita CSRF porque usamos JWT sin estado (stateless)
                .csrf(csrf -> csrf.disable())

                // Configura la sesión como STATELESS — no se guardan sesiones en servidor
                // Cada request debe incluir el token de autenticación
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Permite todas las solicitudes sin autenticación
                // En producción se restringiría por roles con .hasRole()
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}