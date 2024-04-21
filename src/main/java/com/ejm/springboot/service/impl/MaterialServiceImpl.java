package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.Material;
import com.ejm.springboot.repository.MaterialRepository;
import com.ejm.springboot.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    MaterialRepository materialRepository;

    @Override
    public Page<Material> buscarMaterial(String nombre, Pageable pageable) {
        return materialRepository.findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(nombre, pageable);
    }

    @Override
    public boolean guardarMaterial(Material material) {
        try {
            material.setEnabled(true);
            materialRepository.save(material);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<Material> obtenerMaterial(Long id) {
        return materialRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean ocultarMaterial(Long id) {
        Optional<Material> optionalMaterial = materialRepository.findByIdAndEnabledTrue(id);
        if (optionalMaterial.isPresent()) {
            Material material = optionalMaterial.get();
            material.setEnabled(false);
            materialRepository.save(material);
            return true;
        } else {
            return false;
        }
    }

}
