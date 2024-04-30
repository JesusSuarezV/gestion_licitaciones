package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.MaquinariaAPUItemSubproyecto;
import com.ejm.springboot.repository.MaquinariaAPUItemSubproyectoRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.MaquinariaAPUItemSubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MaquinariaAPUItemSubproyectoServiceImpl implements MaquinariaAPUItemSubproyectoService {

    @Autowired
    APUItemSubproyectoService apuItemSubproyectoService;
    @Autowired
    MaquinariaAPUItemSubproyectoRepository maquinariaAPUItemSubproyectoRepository;

    @Override
    public Page<MaquinariaAPUItemSubproyecto> buscarMaquinariaAPUItemSubproyecto(long idAPUItemSubproyecto, String nombre, Pageable pageable) {
        Optional<APUItemSubproyecto> optionalAPUItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto);
        if (optionalAPUItemSubproyecto.isPresent()) {
            APUItemSubproyecto apuItemSubproyecto = optionalAPUItemSubproyecto.get();
            return maquinariaAPUItemSubproyectoRepository.findByApuItemSubproyectoAndEnabledTrueAndMaquinaria_NombreContainingIgnoreCaseOrderByMaquinaria_NombreAsc(apuItemSubproyecto, nombre, pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public boolean guardarMaquinariaAPUItemSubproyecto(MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto) {
        try {
            maquinariaAPUItemSubproyecto.setEnabled(true);
            maquinariaAPUItemSubproyectoRepository.save(maquinariaAPUItemSubproyecto);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<MaquinariaAPUItemSubproyecto> obtenerMaquinariaAPUItemSubproyecto(Long id) {
        return maquinariaAPUItemSubproyectoRepository.findById(id);
    }

    @Override
    public boolean ocultarMaquinariaAPUItemSubproyecto(Long id) {
        Optional<MaquinariaAPUItemSubproyecto> optionalMaquinariaAPUItemSubproyecto = maquinariaAPUItemSubproyectoRepository.findById(id);
        if (optionalMaquinariaAPUItemSubproyecto.isPresent()) {
            MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto = optionalMaquinariaAPUItemSubproyecto.get();
            maquinariaAPUItemSubproyecto.setEnabled(false);
            maquinariaAPUItemSubproyectoRepository.save(maquinariaAPUItemSubproyecto);
            return true;
        } else {
            return false;
        }
    }
}