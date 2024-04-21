package com.ejm.springboot.service;

import com.ejm.springboot.entity.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MaterialService {

    Page<Material> buscarMaterial(String nombre, Pageable pageable);

    boolean guardarMaterial(Material material);

    Optional<Material> obtenerMaterial(Long id);

    boolean ocultarMaterial(Long id);


}
