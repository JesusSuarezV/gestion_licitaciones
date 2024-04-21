package com.ejm.springboot.repository;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    Page<Proyecto> findByCreadorAndEnabledTrueOrderByFechaCreacionDescNombreAsc(Usuario usuario, Pageable pageable);
    Page<Proyecto> findByCreadorAndEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(Usuario creador, String nombre, Pageable pageable);

    Page<Proyecto> findByUsuariosContainsAndEnabledTrueOrderByFechaCreacionDescNombreAsc(Usuario usuario, Pageable pageable);
    Page<Proyecto> findByUsuariosContainsAndEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(Usuario creador, String nombre, Pageable pageable);


    Optional<Proyecto> findByIdAndEnabledTrue(Long id);


}
