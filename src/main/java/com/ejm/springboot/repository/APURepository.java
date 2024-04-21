package com.ejm.springboot.repository;

import com.ejm.springboot.entity.APU;
import com.ejm.springboot.entity.Item;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Subproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface APURepository extends JpaRepository<APU, Long> {
    Page<APU> findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(String nombre, Pageable pageable);

    Optional<APU> findByIdAndEnabledTrue(Long id);

    @Query("SELECT a FROM APU a WHERE a.id NOT IN (SELECT ais.apu.id FROM APUItemSubproyecto ais WHERE ais.enabled = true AND ais.itemSubproyecto = :itemSubproyecto) AND a.enabled = true")
    List<APU> findEnabledAPUNotInAPUItemSubproyectoByItemSubproyecto(ItemSubproyecto itemSubproyecto);


}
