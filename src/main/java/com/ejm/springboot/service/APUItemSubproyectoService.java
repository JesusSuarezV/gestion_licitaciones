package com.ejm.springboot.service;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ItemSubproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface APUItemSubproyectoService {

    Page<APUItemSubproyecto> buscarAPUItemSubproyecto(long idItemSubproyecto, String nombre, Pageable pageable);

    boolean guardarAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto);

    Optional<APUItemSubproyecto> obtenerAPUItemSubproyecto(Long id);

    boolean ocultarAPUItemSubproyecto (Long id);
}
