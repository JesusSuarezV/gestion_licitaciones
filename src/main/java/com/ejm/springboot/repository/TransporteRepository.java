package com.ejm.springboot.repository;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.MaterialAPUItemSubproyecto;
import com.ejm.springboot.entity.Transporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, Long> {

    @Query("SELECT t" +
            "  FROM Transporte t" +
            "  WHERE t.materialAPUItemSubproyecto.apuItemSubproyecto = :apuItemSubproyecto" +
            "  AND t.enabled = true" +
            "  AND (:nombre = '' OR LOWER(t.materialAPUItemSubproyecto.material.nombre) LIKE LOWER(concat('%', :nombre, '%')))" +
            "  ORDER BY LOWER(t.materialAPUItemSubproyecto.material.nombre)")
    Page<Transporte> findByAPUItemSubproyectoAndVisibilidadTrueAndNombreMaterialContainingIgnoreCaseOrderByNombreAsc(APUItemSubproyecto apuItemSubproyecto, String nombre, Pageable pageable);


    @Query("SELECT t" +
            "  FROM Transporte t" +
            "  WHERE t.materialAPUItemSubproyecto.apuItemSubproyecto = :apuItemSubproyecto" +
            "  AND t.enabled = true")
    List<Transporte> findByAPUItemSubproyectoAndEnabledTrue(APUItemSubproyecto apuItemSubproyecto);

    Optional<Transporte> findByIdAndEnabledTrue(Long id);
}
