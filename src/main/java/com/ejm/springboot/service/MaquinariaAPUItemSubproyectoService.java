package com.ejm.springboot.service;

import com.ejm.springboot.entity.MaquinariaAPUItemSubproyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface MaquinariaAPUItemSubproyectoService {

    Page<MaquinariaAPUItemSubproyecto> buscarMaquinariaAPUItemSubproyecto(long idAPUItemSubproyecto, String nombre, Pageable pageable);

    boolean guardarMaquinariaAPUItemSubproyecto(MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto);

    Optional<MaquinariaAPUItemSubproyecto> obtenerMaquinariaAPUItemSubproyecto(Long id);

    boolean ocultarMaquinariaAPUItemSubproyecto(Long id);
}
