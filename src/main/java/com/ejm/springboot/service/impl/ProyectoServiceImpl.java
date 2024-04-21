package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.repository.ProyectoRepository;
import com.ejm.springboot.service.ProyectoService;
import com.ejm.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProyectoServiceImpl implements ProyectoService {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ProyectoRepository proyectoRepository;
    @Override
    public Page<Proyecto> listarMisProyectos(String username, Pageable pageable) {
        return proyectoRepository.findByCreadorAndEnabledTrueOrderByFechaCreacionDescNombreAsc(usuarioService.obtenerUsuarioPorUsername(username), pageable);
    }

    @Override
    public Page<Proyecto> listarProyectos(String username, Pageable pageable) {
        return proyectoRepository.findByUsuariosContainsAndEnabledTrueOrderByFechaCreacionDescNombreAsc(usuarioService.obtenerUsuarioPorUsername(username), pageable);
    }

//    @Override
//    public Page<Proyecto> listarProyectos(String username, Pageable pageable) {
//
//        List<Proyecto> proyectos = usuarioService.obtenerUsuarioPorUsername(username).getProyectos().stream()
//                .filter(Proyecto::isEnabled)
//                .sorted(Comparator.comparing(Proyecto::getFechaCreacion).reversed())
//                .collect(Collectors.toList());
//        return new PageImpl<>(proyectos, pageable, proyectos.size());
//    }

    @Override
    public Page<Proyecto> buscarMisProyectos(String keyword, String username, Pageable pageable) {
        return proyectoRepository.findByCreadorAndEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(usuarioService.obtenerUsuarioPorUsername(username), keyword, pageable);
    }

    @Override
    public Page<Proyecto> buscarProyectos(String keyword, String username, Pageable pageable) {
        return proyectoRepository.findByUsuariosContainsAndEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(usuarioService.obtenerUsuarioPorUsername(username), keyword, pageable);

    }

    @Override
    public Optional<Proyecto> obtenerProyecto(Long id) {
        return proyectoRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean guardarProyecto(Proyecto proyecto) {

        try {
            proyecto.setEnabled(true);
            proyectoRepository.save(proyecto);

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;

        }
    }

    @Override
    public boolean ocultarProyecto(Long id) {
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);

        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setEnabled(false);
            proyectoRepository.save(proyecto);
            return true;
        } else {
            return false;
        }
    }


}
