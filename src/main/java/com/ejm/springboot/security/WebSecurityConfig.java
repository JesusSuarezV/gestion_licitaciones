package com.ejm.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

 @Configuration
 @EnableWebSecurity
 public class WebSecurityConfig{

        @Autowired
        private DataSource dataSource;

        @Autowired
        public void configAuthentication(AuthenticationManagerBuilder builder) throws Exception {
            builder.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                    .dataSource(dataSource)
                    .usersByUsernameQuery("select username, password, enabled from usuario where username=?")
                    .authoritiesByUsernameQuery("select username, role from usuario where username=?");
        }

        @Bean
        protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeHttpRequests(
                            auth -> auth
                                    .requestMatchers(
                                            "/js/**",
                                            "/css/**",
                                            "/imagenes/**",
                                            "/").permitAll()

                                    //.requestMatchers("/personas/nueva").hasAnyRole("ADMIN")
                                    //.requestMatchers("/personas/editar/*","/personas/eliminar/*").hasRole("ADMIN")
                                    .requestMatchers("/Proyectos/Mis_Proyectos/**", "/Proyectos/Crear_Proyectos", "/Proyectos/Guardar", "Proyectos/{id}/Editar","Proyectos/{id}/Eliminar", "Proyectos/{id}/Editores").hasAnyAuthority("SADMIN", "ADMIN")
                                    .requestMatchers("/Usuarios/**", "/Items/**", "/APU/**", "/Materiales/**", "/Maquinarias/**", "/ManoObras/**").hasAuthority("SADMIN")
                                    .requestMatchers("/Registro", "/Iniciar_Sesion", "/Recuperar_Contrasena/**", "/Confirmacion/**", "/Recuperacion/**").anonymous()

                                    .anyRequest().authenticated()
                                    )
                    .formLogin(form -> form
                            .loginPage("/Iniciar_Sesion"))
                    .logout((logout) -> logout.logoutSuccessUrl("/?logout").permitAll()) ;
                    //.exceptionHandling(e -> e.accessDeniedPage("/403"));
            return httpSecurity.build();
        }
     @Bean
     public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
     }
    }


