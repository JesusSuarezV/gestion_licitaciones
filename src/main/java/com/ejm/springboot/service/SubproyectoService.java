package com.ejm.springboot.service;

import com.ejm.springboot.entity.Subproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SubproyectoService {
    Page<Subproyecto> buscarSubproyectos(String nombre, long idProyecto, Pageable pageable);

    boolean guardarSubproyecto(Subproyecto subproyecto);

    Optional<Subproyecto> obtenerSubproyecto(long id);

    boolean ocultarProyecto(Long id);
}
