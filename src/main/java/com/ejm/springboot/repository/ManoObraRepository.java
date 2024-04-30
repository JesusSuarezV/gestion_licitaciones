package com.ejm.springboot.repository;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ManoObra;
import com.ejm.springboot.entity.Maquinaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManoObraRepository extends JpaRepository<ManoObra, Long> {

    Page<ManoObra> findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(String nombre, Pageable pageable);

    Optional<ManoObra> findByIdAndEnabledTrue(Long id);

    @Query("SELECT m FROM ManoObra m WHERE m.id NOT IN (SELECT mais.manoObra.id FROM ManoObraAPUItemSubproyecto mais WHERE mais.enabled = true AND mais.apuItemSubproyecto = :apuItemSubproyecto) AND m.enabled = true")
    List<ManoObra> findEnabledManoObraNotInManoObraAPUItemSubproyectoByAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto);
}
