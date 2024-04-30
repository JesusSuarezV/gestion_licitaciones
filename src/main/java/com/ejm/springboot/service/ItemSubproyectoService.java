package com.ejm.springboot.service;

import com.ejm.springboot.entity.ItemSubproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemSubproyectoService {
    Page<ItemSubproyecto> buscarItemSubproyecto(long idSubproyecto, String nombre, Pageable pageable);

    boolean guardarItemSubproyecto(ItemSubproyecto itemSubproyecto);

    Optional<ItemSubproyecto> obtenerItemSubproyecto(Long id);

    boolean ocultarItemSubproyecto (Long id);

}
