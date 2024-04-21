package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.APU;
import com.ejm.springboot.repository.APURepository;
import com.ejm.springboot.service.APUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class APUServiceImpl implements APUService {

    @Autowired
    APURepository apuRepository;

    @Override
    public Page<APU> buscarAPU(String nombre, Pageable pageable) {
        return apuRepository.findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(nombre, pageable);
    }

    @Override
    public boolean guardarAPU(APU apu) {
        try {
            apu.setEnabled(true);
            apuRepository.save(apu);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<APU> obtenerAPU(Long id) {
        return apuRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean ocultarAPU(Long id) {
        Optional<APU> optionalAPU = apuRepository.findByIdAndEnabledTrue(id);
        if (optionalAPU.isPresent()) {
            APU apu = optionalAPU.get();
            apu.setEnabled(false);
            apuRepository.save(apu);
            return true;
        } else return false;
    }
}
