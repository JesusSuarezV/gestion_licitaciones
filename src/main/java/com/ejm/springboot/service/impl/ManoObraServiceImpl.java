package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.ManoObra;
import com.ejm.springboot.repository.ManoObraRepository;
import com.ejm.springboot.service.ManoObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ManoObraServiceImpl implements ManoObraService {

    @Autowired
    private ManoObraRepository manoObraRepository;

    @Override
    public Page<ManoObra> buscarManoObra(String nombre, Pageable pageable) {
        return manoObraRepository.findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(nombre, pageable);
    }

    @Override
    public boolean guardarManoObra(ManoObra manoObra) {
        try {
            manoObra.setEnabled(true);
            manoObraRepository.save(manoObra);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<ManoObra> obtenerManoObra(Long id) {
        return manoObraRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean ocultarManoObra(Long id) {
        Optional<ManoObra> optionalManoObra = manoObraRepository.findByIdAndEnabledTrue(id);
        if (optionalManoObra.isPresent()) {
            ManoObra manoObra = optionalManoObra.get();
            manoObra.setEnabled(false);
            manoObraRepository.save(manoObra);
            return true;
        } else {
            return false;
        }
    }
}