package com.ejm.springboot.repository;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.MaterialAPUItemSubproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialAPUItemSubproyectoRepository extends JpaRepository<MaterialAPUItemSubproyecto, Long> {
    Page<MaterialAPUItemSubproyecto> findByapuItemSubproyectoAndEnabledTrueAndMaterial_NombreContainingIgnoreCaseOrderByMaterial_NombreAsc(APUItemSubproyecto apuItemSubproyecto, String nombre, Pageable pageable);
    List<MaterialAPUItemSubproyecto> findByapuItemSubproyectoAndEnabledTrue(APUItemSubproyecto apuItemSubproyecto);
    Optional<MaterialAPUItemSubproyecto> findByIdAndEnabledTrue(Long id);

    @Query("SELECT m FROM MaterialAPUItemSubproyecto m WHERE m NOT IN (SELECT t.materialAPUItemSubproyecto FROM Transporte t WHERE t.materialAPUItemSubproyecto.apuItemSubproyecto = :apuItemSubproyecto AND t.enabled = true) AND m.apuItemSubproyecto = :apuItemSubproyecto AND m.enabled = true")
    List<MaterialAPUItemSubproyecto> findMaterialesAsignadosNotInTransporte(APUItemSubproyecto apuItemSubproyecto);

}
