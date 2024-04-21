package com.ejm.springboot.repository;

import com.ejm.springboot.entity.Maquinaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaquinariaRepository extends JpaRepository<Maquinaria, Long> {

    Page<Maquinaria> findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(String nombre, Pageable pageable);

    Optional<Maquinaria> findByIdAndEnabledTrue(Long id);
}
