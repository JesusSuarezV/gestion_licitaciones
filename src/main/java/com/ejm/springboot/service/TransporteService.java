package com.ejm.springboot.service;

import com.ejm.springboot.entity.Transporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransporteService {

    public Page<Transporte> buscarTransporte(long idAPUItemSubproyecto, String nombre, Pageable pageable);

    public boolean guardarTransporte(Transporte transporte);

    public Optional<Transporte> obtenerTransporte(Long id);

    public boolean ocultarTransporte(Long id);
}
