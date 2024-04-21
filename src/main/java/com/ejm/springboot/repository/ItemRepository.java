package com.ejm.springboot.repository;

import com.ejm.springboot.entity.Item;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Subproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(String nombre, Pageable pageable);

    Optional<Item> findByIdAndEnabledTrue(Long id);


    @Query("SELECT i FROM Item i WHERE i.id NOT IN (SELECT is.item.id FROM ItemSubproyecto is WHERE is.subproyecto = :subproyecto AND is.enabled = true) AND i.enabled = true")
    List<Item> findItemsNotInSubproyecto(Subproyecto subproyecto);


}
