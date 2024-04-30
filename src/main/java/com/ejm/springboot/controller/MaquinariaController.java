package com.ejm.springboot.controller;

import com.ejm.springboot.entity.Maquinaria;
import com.ejm.springboot.service.MaquinariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/Maquinarias")
public class MaquinariaController {

    @Autowired
    private MaquinariaService maquinariaService;

    @GetMapping
    public String verMaquinarias(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("maquinarias", maquinariaService.buscarMaquinaria(keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "Maquinarias/Ver_Maquinarias";
    }

    @GetMapping("/Nueva_Maquinaria")
    public String verFormularioDeRegistrarMaquinaria(Model model) {
        model.addAttribute("maquinaria", new Maquinaria());
        return "Maquinarias/Formulario_Maquinaria";
    }

    @PostMapping("/Guardar")
    public String guardarMaquinaria(@ModelAttribute Maquinaria maquinaria) {
        if (maquinaria.getId() == null) {
            maquinaria.setFechaCreacion(LocalDate.now());
        }
        if (maquinariaService.guardarMaquinaria(maquinaria)) {
            return "redirect:/Maquinarias?exitoGuardado";
        } else {
            return "redirect:/Maquinarias?errorGuardado";
        }
    }

    @GetMapping("/{id}/Editar")
    public String verFormularioDeEditarMaquinaria(@PathVariable Long id, Model model) {
        Optional<Maquinaria> optionalMaquinaria = maquinariaService.obtenerMaquinaria(id);
        if (optionalMaquinaria.isPresent()) {
            model.addAttribute("maquinaria", optionalMaquinaria.get());
            return "Maquinarias/Formulario_Maquinaria";
        } else {
            return "redirect:/Maquinarias?errorMaquinaria";
        }
    }

    @GetMapping("/{id}/Eliminar")
    public String ocultarMaquinaria(@PathVariable Long id, Model model) {
        if (maquinariaService.ocultarMaquinaria(id)) {
            return "redirect:/Maquinarias?exitoOcultar";
        } else {
            return "redirect:/Maquinarias?errorOcultar";
        }
    }
}
