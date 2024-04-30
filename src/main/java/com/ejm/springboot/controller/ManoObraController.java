package com.ejm.springboot.controller;

import com.ejm.springboot.entity.ManoObra;
import com.ejm.springboot.service.ManoObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/ManoObras")
public class ManoObraController {

    @Autowired
    private ManoObraService manoObraService;

    @GetMapping
    public String verManoObras(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("manoObras", manoObraService.buscarManoObra(keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "ManoObras/Ver_ManoObras";
    }

    @GetMapping("/Nueva_ManoObra")
    public String verFormularioDeRegistrarManoObra(Model model) {
        model.addAttribute("manoObra", new ManoObra());
        return "ManoObras/Formulario_ManoObra";
    }

    @PostMapping("/Guardar")
    public String guardarManoObra(@ModelAttribute ManoObra manoObra) {
        if (manoObra.getId() == null) {
            manoObra.setFechaCreacion(LocalDate.now());
        }
        if (manoObraService.guardarManoObra(manoObra)) {
            return "redirect:/ManoObras?exitoGuardado";
        } else {
            return "redirect:/ManoObras?errorGuardado";
        }
    }

    @GetMapping("/{id}/Editar")
    public String verFormularioDeEditarManoObra(@PathVariable Long id, Model model) {
        Optional<ManoObra> optionalManoObra = manoObraService.obtenerManoObra(id);
        if (optionalManoObra.isPresent()) {
            model.addAttribute("manoObra", optionalManoObra.get());
            return "ManoObras/Formulario_ManoObra";
        } else {
            return "redirect:/ManoObras?errorManoObra";
        }
    }

    @GetMapping("/{id}/Eliminar")
    public String ocultarManoObra(@PathVariable Long id, Model model) {
        if (manoObraService.ocultarManoObra(id)) {
            return "redirect:/ManoObras?exitoOcultar";
        } else {
            return "redirect:/ManoObras?errorOcultar";
        }
    }
}
