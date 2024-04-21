package com.ejm.springboot.service;

import com.ejm.springboot.entity.APU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface APUService {

    Page<APU> buscarAPU(String nombre, Pageable pageable);

    boolean guardarAPU(APU apu);

    Optional<APU> obtenerAPU(Long id);

    boolean ocultarAPU(Long id);
}
