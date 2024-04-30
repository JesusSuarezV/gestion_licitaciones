package com.ejm.springboot.repository;

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
public interface ItemSubproyectoRepository extends JpaRepository<ItemSubproyecto, Long> {

    Page<ItemSubproyecto> findBySubproyectoAndEnabledTrueAndItem_NombreContainingIgnoreCaseOrderByItem_NombreAsc(Subproyecto subproyecto, String nombre, Pageable pageable);
    List<ItemSubproyecto> findBySubproyectoAndEnabledTrueOrderByItem_NombreAsc(Subproyecto subproyecto);
    Optional<ItemSubproyecto> findByIdAndEnabledTrue(Long id);

}
