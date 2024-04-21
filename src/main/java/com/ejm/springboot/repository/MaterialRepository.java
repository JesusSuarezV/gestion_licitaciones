package com.ejm.springboot.repository;

import com.ejm.springboot.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(String nombre, Pageable pageable);

    Optional<Material> findByIdAndEnabledTrue(Long id);
}
