package com.ejm.springboot.service;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsuarioService {

    Boolean guardarUsuario(Usuario usuario);

    Boolean cambiarContraseña(Usuario usuario, String password);

    Usuario obtenerUsuarioPorUsername(String username);

    Page<Usuario> buscarUsuario(String username, Pageable pageable);

    Optional<Usuario> seleccionarUsuario(Long id);

    boolean editarRol(Long id, String role);

    boolean bloquearUsuario(Long id);

    Page<Usuario> buscarEditor (Proyecto proyecto, String keyword, Pageable pageable);



}
