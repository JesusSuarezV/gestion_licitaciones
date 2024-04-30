package com.ejm.springboot.repository;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ManoObraAPUItemSubproyecto;
import com.ejm.springboot.entity.MaterialAPUItemSubproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManoObraAPUItemSubproyectoRepository extends JpaRepository<ManoObraAPUItemSubproyecto, Long> {


    Page<ManoObraAPUItemSubproyecto> findByapuItemSubproyectoAndEnabledTrueAndManoObra_NombreContainingIgnoreCaseOrderByManoObra_NombreAsc(APUItemSubproyecto apuItemSubproyecto, String nombre, Pageable pageable);

    List<ManoObraAPUItemSubproyecto> findByapuItemSubproyectoAndEnabledTrue(APUItemSubproyecto apuItemSubproyecto);

    Optional<ManoObraAPUItemSubproyecto> findByIdAndEnabledTrue(Long id);
}


