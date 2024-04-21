package com.ejm.springboot.service;

import com.ejm.springboot.entity.Maquinaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface MaquinariaService {

    Page<Maquinaria> buscarMaquinaria(String nombre, Pageable pageable);

    boolean guardarMaquinaria(Maquinaria maquinaria);

    Optional<Maquinaria> obtenerMaquinaria(Long id);

    boolean ocultarMaquinaria(Long id);
}
