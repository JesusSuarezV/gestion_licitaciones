package com.ejm.springboot.service;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EditorService {

    Page<Usuario> buscarEditor (Proyecto proyecto, String keyword, Pageable pageable);

    boolean asignarEditor(Proyecto proyecto, String correo);

    boolean ocultarEditor(Proyecto proyecto, Long id);

}
