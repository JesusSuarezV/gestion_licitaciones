package com.ejm.springboot.repository;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.Item;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Subproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface APUItemSubproyectoRepository extends JpaRepository<APUItemSubproyecto, Long> {
    Page<APUItemSubproyecto> findByItemSubproyectoAndEnabledTrueAndApu_NombreContainingIgnoreCaseOrderByApu_NombreAsc(ItemSubproyecto itemSubproyecto, String nombre, Pageable pageable);

    List<APUItemSubproyecto> findByItemSubproyectoAndEnabledTrueOrderByApu_NombreAsc(ItemSubproyecto itemSubproyecto);

    Optional<APUItemSubproyecto> findByIdAndEnabledTrue(Long id);

}
