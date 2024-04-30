package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Material;
import com.ejm.springboot.entity.MaterialAPUItemSubproyecto;
import com.ejm.springboot.repository.APUItemSubproyectoRepository;
import com.ejm.springboot.repository.MaterialAPUItemSubproyectoRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.ItemSubproyectoService;
import com.ejm.springboot.service.MaterialAPUItemSubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaterialAPUItemSubproyectoServiceImpl implements MaterialAPUItemSubproyectoService {

    @Autowired
    APUItemSubproyectoService apuItemSubproyectoService;
    @Autowired
    MaterialAPUItemSubproyectoRepository materialAPUItemSubproyectoRepository;

    @Override
    public Page<MaterialAPUItemSubproyecto> buscarMaterialAPUItemSubproyecto(long idAPUItemSubproyecto, String nombre, Pageable pageable) {
        Optional<APUItemSubproyecto> optionalAPUItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto);
        if (optionalAPUItemSubproyecto.isPresent()) {
            APUItemSubproyecto apuItemSubproyecto = optionalAPUItemSubproyecto.get();
            return materialAPUItemSubproyectoRepository.findByapuItemSubproyectoAndEnabledTrueAndMaterial_NombreContainingIgnoreCaseOrderByMaterial_NombreAsc(apuItemSubproyecto, nombre, pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public boolean guardarMaterialAPUItemSubproyecto(MaterialAPUItemSubproyecto materialAPUItemSubproyecto) {
        try {
            materialAPUItemSubproyecto.setEnabled(true);
            materialAPUItemSubproyectoRepository.save(materialAPUItemSubproyecto);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<MaterialAPUItemSubproyecto> obtenerMaterialAPUItemSubproyecto(Long id) {
        return materialAPUItemSubproyectoRepository.findById(id);
    }

    @Override
    public boolean ocultarMaterialAPUItemSubproyecto(Long id) {
        Optional<MaterialAPUItemSubproyecto> optionalMaterialAPUItemSubproyecto = materialAPUItemSubproyectoRepository.findById(id);
        if (optionalMaterialAPUItemSubproyecto.isPresent()) {
            MaterialAPUItemSubproyecto materialAPUItemSubproyecto = optionalMaterialAPUItemSubproyecto.get();
            materialAPUItemSubproyecto.setEnabled(false);
            materialAPUItemSubproyectoRepository.save(materialAPUItemSubproyecto);
            return true;
        } else {
            return false;
        }
    }
}
