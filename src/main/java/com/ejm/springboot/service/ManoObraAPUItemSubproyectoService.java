package com.ejm.springboot.service;

import com.ejm.springboot.entity.ManoObraAPUItemSubproyecto;
import com.ejm.springboot.entity.MaquinariaAPUItemSubproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ManoObraAPUItemSubproyectoService {

    Page<ManoObraAPUItemSubproyecto> buscarManoObraAPUItemSubproyecto(long idAPUItemSubproyecto, String nombre, Pageable pageable);

    boolean guardarManoObraAPUItemSubproyecto(ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto);

    Optional<ManoObraAPUItemSubproyecto> obtenerManoObraAPUItemSubproyecto(Long id);

    boolean ocultarManoObraAPUItemSubproyecto(Long id);
}
