package com.ejm.springboot.repository;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.Maquinaria;
import com.ejm.springboot.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaquinariaRepository extends JpaRepository<Maquinaria, Long> {

    Page<Maquinaria> findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(String nombre, Pageable pageable);

    Optional<Maquinaria> findByIdAndEnabledTrue(Long id);

    @Query("SELECT m FROM Maquinaria m WHERE m.id NOT IN (SELECT mais.maquinaria.id FROM MaquinariaAPUItemSubproyecto mais WHERE mais.enabled = true AND mais.apuItemSubproyecto = :apuItemSubproyecto) AND m.enabled = true")
    List<Maquinaria> findEnabledMaquinariaNotInMaquinariaAPUItemSubproyectoByAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto);
}
