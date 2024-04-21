package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Usuario;
import com.ejm.springboot.repository.ProyectoRepository;
import com.ejm.springboot.repository.UsuarioRepository;
import com.ejm.springboot.service.EditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EditorServiceImpl implements EditorService {

    @Autowired
    ProyectoRepository proyectoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> buscarEditor(Proyecto proyecto, String keyword, Pageable pageable) {
        return usuarioRepository.findByProyectosContainsAndEnabledTrueAndNombreContainingIgnoreCaseOrderByUsernameAsc(proyecto, keyword, pageable);
    }

    @Override
    public boolean asignarEditor(Proyecto proyecto, String correo) {
        try {
            Usuario usuario = usuarioRepository.findByUsername(correo);
            System.out.println(usuario.getNombre());
            if (usuario == proyecto.getCreador()) return false;
            proyecto.getUsuarios().add(usuario);
            proyectoRepository.save(proyecto);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean ocultarEditor(Proyecto proyecto, Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByIdAndEnabledTrue(id);
        if (optionalUsuario.isPresent()) {
            proyecto.getUsuarios().remove(optionalUsuario.get());
            proyectoRepository.save(proyecto);
            return true;
        } else {
            return false;
        }
    }
}
