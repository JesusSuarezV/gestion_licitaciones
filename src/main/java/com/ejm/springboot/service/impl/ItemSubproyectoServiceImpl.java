package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Subproyecto;
import com.ejm.springboot.repository.APUItemSubproyectoRepository;
import com.ejm.springboot.repository.ItemSubproyectoRepository;
import com.ejm.springboot.repository.SubproyectoRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.ItemSubproyectoService;
import com.ejm.springboot.service.SubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemSubproyectoServiceImpl implements ItemSubproyectoService {
    @Autowired
    SubproyectoService subproyectoService;
    @Autowired
    ItemSubproyectoRepository itemSubproyectoRepository;
    @Override
    public Page<ItemSubproyecto> buscarItemSubproyecto(long idSubproyecto, String nombre, Pageable pageable) {
        Optional<Subproyecto> optionalSubproyecto = subproyectoService.obtenerSubproyecto(idSubproyecto);
        if (optionalSubproyecto.isPresent()) {
            Subproyecto subproyecto = optionalSubproyecto.get();
            return itemSubproyectoRepository.findBySubproyectoAndEnabledTrueAndItem_NombreContainingIgnoreCaseOrderByItem_NombreAsc(subproyecto, nombre, pageable);
        } else return null;
    }

    @Override
    public boolean guardarItemSubproyecto(ItemSubproyecto itemSubproyecto) {
        try {
            itemSubproyecto.setEnabled(true);
            itemSubproyectoRepository.save(itemSubproyecto);

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;

        }
    }

    @Override
    public Optional<ItemSubproyecto> obtenerItemSubproyecto(Long id) {
        return itemSubproyectoRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean ocultarItemSubproyecto(Long id) {
        Optional<ItemSubproyecto> optionalItemSubproyecto = itemSubproyectoRepository.findByIdAndEnabledTrue(id);
        if (optionalItemSubproyecto.isPresent()) {
            ItemSubproyecto itemSubproyecto = optionalItemSubproyecto.get();
            itemSubproyecto.setEnabled(false);
            itemSubproyectoRepository.save(itemSubproyecto);
            return true;
        } else {
            return false;
        }
    }

}
