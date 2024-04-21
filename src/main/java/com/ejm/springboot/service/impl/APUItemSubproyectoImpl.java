package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.repository.APUItemSubproyectoRepository;
import com.ejm.springboot.repository.ItemSubproyectoRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.ItemSubproyectoService;
import com.ejm.springboot.service.SubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class APUItemSubproyectoImpl implements APUItemSubproyectoService {

    @Autowired
    ItemSubproyectoService itemSubproyectoService;
    @Autowired
    APUItemSubproyectoRepository apuItemSubproyectoRepository;

    @Override
    public Page<APUItemSubproyecto> buscarAPUItemSubproyecto(long idItemSubproyecto, String nombre, Pageable pageable) {
        Optional<ItemSubproyecto> optionalItemSubproyecto = itemSubproyectoService.obtenerItemSubproyecto(idItemSubproyecto);
        if (optionalItemSubproyecto.isPresent()) {
            ItemSubproyecto itemSubproyecto = optionalItemSubproyecto.get();
            return apuItemSubproyectoRepository.findByItemSubproyectoAndEnabledTrueAndApu_NombreContainingIgnoreCaseOrderByApu_NombreAsc(itemSubproyecto, nombre, pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public boolean guardarAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto) {
        try {
            apuItemSubproyecto.setEnabled(true);
            apuItemSubproyectoRepository.save(apuItemSubproyecto);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<APUItemSubproyecto> obtenerAPUItemSubproyecto(Long id) {
        return apuItemSubproyectoRepository.findById(id);
    }

    @Override
    public boolean ocultarAPUItemSubproyecto(Long id) {
        Optional<APUItemSubproyecto> optionalAPUItemSubproyecto = apuItemSubproyectoRepository.findById(id);
        if (optionalAPUItemSubproyecto.isPresent()) {
            APUItemSubproyecto apuItemSubproyecto = optionalAPUItemSubproyecto.get();
            apuItemSubproyecto.setEnabled(false);
            apuItemSubproyectoRepository.save(apuItemSubproyecto);
            return true;
        } else {
            return false;
        }
    }
}
