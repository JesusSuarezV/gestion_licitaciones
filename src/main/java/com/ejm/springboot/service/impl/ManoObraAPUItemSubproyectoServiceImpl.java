package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ManoObra;
import com.ejm.springboot.entity.ManoObraAPUItemSubproyecto;
import com.ejm.springboot.repository.APUItemSubproyectoRepository;
import com.ejm.springboot.repository.ManoObraAPUItemSubproyectoRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.ManoObraAPUItemSubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManoObraAPUItemSubproyectoServiceImpl implements ManoObraAPUItemSubproyectoService {

    @Autowired
    APUItemSubproyectoService apuItemSubproyectoService;
    @Autowired
    ManoObraAPUItemSubproyectoRepository manoObraAPUItemSubproyectoRepository;

    @Override
    public Page<ManoObraAPUItemSubproyecto> buscarManoObraAPUItemSubproyecto(long idAPUItemSubproyecto, String nombre, Pageable pageable) {
        Optional<APUItemSubproyecto> optionalAPUItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto);
        if (optionalAPUItemSubproyecto.isPresent()) {
            APUItemSubproyecto apuItemSubproyecto = optionalAPUItemSubproyecto.get();
            return manoObraAPUItemSubproyectoRepository.findByapuItemSubproyectoAndEnabledTrueAndManoObra_NombreContainingIgnoreCaseOrderByManoObra_NombreAsc(apuItemSubproyecto, nombre, pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public boolean guardarManoObraAPUItemSubproyecto(ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto) {
        try {
            manoObraAPUItemSubproyecto.setEnabled(true);
            manoObraAPUItemSubproyectoRepository.save(manoObraAPUItemSubproyecto);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<ManoObraAPUItemSubproyecto> obtenerManoObraAPUItemSubproyecto(Long id) {
        return manoObraAPUItemSubproyectoRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean ocultarManoObraAPUItemSubproyecto(Long id) {
        Optional<ManoObraAPUItemSubproyecto> optionalManoObraAPUItemSubproyecto = manoObraAPUItemSubproyectoRepository.findByIdAndEnabledTrue(id);
        if (optionalManoObraAPUItemSubproyecto.isPresent()) {
            ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto = optionalManoObraAPUItemSubproyecto.get();
            manoObraAPUItemSubproyecto.setEnabled(false);
            manoObraAPUItemSubproyectoRepository.save(manoObraAPUItemSubproyecto);
            return true;
        } else {
            return false;
        }
    }
}
