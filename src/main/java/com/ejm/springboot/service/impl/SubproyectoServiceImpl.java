package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Subproyecto;
import com.ejm.springboot.repository.SubproyectoRepository;
import com.ejm.springboot.service.ProyectoService;
import com.ejm.springboot.service.SubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubproyectoServiceImpl implements SubproyectoService {
    @Autowired
    ProyectoService proyectoService;
    @Autowired
    SubproyectoRepository subproyectoRepository;

    @Override
    public Page<Subproyecto> buscarSubproyectos(String nombre, long idProyecto, Pageable pageable) {
        Optional<Proyecto> optionalProyecto = proyectoService.obtenerProyecto(idProyecto);

        if (optionalProyecto.isPresent()) {

            return subproyectoRepository.findByProyectoAndEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(optionalProyecto.get(), nombre, pageable);
        } else return null;

    }

    @Override
    public boolean guardarSubproyecto(Subproyecto subproyecto) {
        try {
            subproyecto.setEnabled(true);
            subproyectoRepository.save(subproyecto);

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;

        }
    }

    @Override
    public Optional<Subproyecto> obtenerSubproyecto(long id) {
        return subproyectoRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean ocultarProyecto(Long id) {
        Optional<Subproyecto> optionalSubproyecto = subproyectoRepository.findByIdAndEnabledTrue(id);
        if (optionalSubproyecto.isPresent()) {
            Subproyecto subproyecto = optionalSubproyecto.get();
            subproyecto.setEnabled(false);
            subproyectoRepository.save(subproyecto);
            return true;
        } else {
            return false;
        }
    }
}
