package com.ejm.springboot.repository;

import com.ejm.springboot.entity.APU;
import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(String nombre, Pageable pageable);

    Optional<Material> findByIdAndEnabledTrue(Long id);

    @Query("SELECT m FROM Material m WHERE m.id NOT IN (SELECT mais.material.id FROM MaterialAPUItemSubproyecto mais WHERE mais.enabled = true AND mais.apuItemSubproyecto = :apuItemSubproyecto) AND m.enabled = true")
    List<Material> findEnabledMaterialNotInMaterialAPUItemSubproyectoByAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto);
}
