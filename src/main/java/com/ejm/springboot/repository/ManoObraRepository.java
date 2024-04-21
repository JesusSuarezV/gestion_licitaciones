package com.ejm.springboot.repository;

import com.ejm.springboot.entity.ManoObra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManoObraRepository extends JpaRepository<ManoObra, Long> {

    Page<ManoObra> findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(String nombre, Pageable pageable);

    Optional<ManoObra> findByIdAndEnabledTrue(Long id);
}
