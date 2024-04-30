package com.ejm.springboot.repository;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.MaquinariaAPUItemSubproyecto;
import com.ejm.springboot.entity.MaterialAPUItemSubproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaquinariaAPUItemSubproyectoRepository extends JpaRepository<MaquinariaAPUItemSubproyecto, Long> {
    Page<MaquinariaAPUItemSubproyecto> findByApuItemSubproyectoAndEnabledTrueAndMaquinaria_NombreContainingIgnoreCaseOrderByMaquinaria_NombreAsc(APUItemSubproyecto apuItemSubproyecto, String nombre, Pageable pageable);
    List<MaquinariaAPUItemSubproyecto> findByapuItemSubproyectoAndEnabledTrue(APUItemSubproyecto apuItemSubproyecto);
    Optional<MaquinariaAPUItemSubproyecto> findByIdAndEnabledTrue(Long id);
}
