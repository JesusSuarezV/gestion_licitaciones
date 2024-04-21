package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.Maquinaria;
import com.ejm.springboot.repository.MaquinariaRepository;
import com.ejm.springboot.service.MaquinariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MaquinariaServiceImpl implements MaquinariaService {

    @Autowired
    private MaquinariaRepository maquinariaRepository;

    @Override
    public Page<Maquinaria> buscarMaquinaria(String nombre, Pageable pageable) {
        return maquinariaRepository.findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(nombre, pageable);
    }

    @Override
    public boolean guardarMaquinaria(Maquinaria maquinaria) {
        try {
            maquinaria.setEnabled(true);
            maquinariaRepository.save(maquinaria);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<Maquinaria> obtenerMaquinaria(Long id) {
        return maquinariaRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean ocultarMaquinaria(Long id) {
        Optional<Maquinaria> optionalMaquinaria = maquinariaRepository.findByIdAndEnabledTrue(id);
        if (optionalMaquinaria.isPresent()) {
            Maquinaria maquinaria = optionalMaquinaria.get();
            maquinaria.setEnabled(false);
            maquinariaRepository.save(maquinaria);
            return true;
        } else {
            return false;
        }
    }
}
