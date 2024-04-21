package com.ejm.springboot.service;

import com.ejm.springboot.entity.ManoObra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ManoObraService {

    Page<ManoObra> buscarManoObra(String nombre, Pageable pageable);

    boolean guardarManoObra(ManoObra manoObra);

    Optional<ManoObra> obtenerManoObra(Long id);

    boolean ocultarManoObra(Long id);
}
