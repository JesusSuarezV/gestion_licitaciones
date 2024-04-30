package com.ejm.springboot.service;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.Material;
import com.ejm.springboot.entity.MaterialAPUItemSubproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MaterialAPUItemSubproyectoService {

    Page<MaterialAPUItemSubproyecto> buscarMaterialAPUItemSubproyecto(long idAPUItemSubproyecto, String nombre, Pageable pageable);

    boolean guardarMaterialAPUItemSubproyecto(MaterialAPUItemSubproyecto materialAPUItemSubproyecto);

    Optional<MaterialAPUItemSubproyecto> obtenerMaterialAPUItemSubproyecto(Long id);

    boolean ocultarMaterialAPUItemSubproyecto (Long id);

}
