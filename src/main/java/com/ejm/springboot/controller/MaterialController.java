package com.ejm.springboot.controller;

import com.ejm.springboot.entity.Material;
import com.ejm.springboot.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/Materiales")
public class MaterialController {
    @Autowired
    MaterialService materialService;

    @GetMapping
    public String verMateriales(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("materiales", materialService.buscarMaterial(keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "Materiales/Ver_Materiales";
    }

    @GetMapping("/Nuevo_Material")
    public String verFormularioDeRegistrarMaterial(Model model) {
        model.addAttribute("material", new Material());
        return "Materiales/Formulario_Material";
    }

    @PostMapping("/Guardar")
    public String guardarMaterial(@ModelAttribute Material material) {
        if (material.getId() == null) {
            material.setFechaCreacion(LocalDate.now());
        }
        if (materialService.guardarMaterial(material)) {
            return "redirect:/Materiales?exitoGuardado";
        } else {
            return "redirect:/Materiales?errorGuardado";
        }
    }

    @GetMapping("/{id}/Editar")
    public String verFormularioDeEditarMaterial(@PathVariable Long id, Model model) {
        Optional<Material> optionalMaterial = materialService.obtenerMaterial(id);
        if (optionalMaterial.isPresent()) {
            model.addAttribute("material", optionalMaterial.get());
            return "Materiales/Formulario_Material";
        } else {
            return "redirect:/Materiales?errorMaterial";
        }
    }

    @GetMapping("/{id}/Eliminar")
    public String ocultarMaterial(@PathVariable Long id, Model model) {
        if (materialService.ocultarMaterial(id)) {
            return "redirect:/Materiales?exitoOcultar";
        } else {
            return "redirect:/Materiales?errorOcultar";
        }
    }

}
