package com.ejm.springboot.repository;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Subproyecto;
import com.ejm.springboot.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubproyectoRepository extends JpaRepository<Subproyecto, Long> {
    Page<Subproyecto> findByProyectoAndEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(Proyecto proyecto, String nombre, Pageable pageable);
    List<Subproyecto> findByProyectoAndEnabledTrueOrderByNombreAsc(Proyecto proyecto);
    Optional<Subproyecto> findByIdAndEnabledTrue(Long id);

}
