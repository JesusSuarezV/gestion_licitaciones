package com.ejm.springboot.service;

import com.ejm.springboot.entity.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProyectoService {

    Page<Proyecto> listarMisProyectos(String username, Pageable pageable);

    Page<Proyecto> listarProyectos(String username, Pageable pageable);

    Page<Proyecto> buscarMisProyectos(String keyword, String username, Pageable pageable);

    Page<Proyecto> buscarProyectos(String keyword, String username, Pageable pageable);

    Optional<Proyecto> obtenerProyecto(Long id);

    boolean guardarProyecto(Proyecto proyecto);

    boolean ocultarProyecto(Long id);
}
