package com.ejm.springboot.controller;

import com.ejm.springboot.entity.APU;
import com.ejm.springboot.entity.Item;
import com.ejm.springboot.service.APUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/APU")
public class APUController {
    @Autowired
    APUService apuService;

    @GetMapping
    public String verAPU(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {

        model.addAttribute("apus", apuService.buscarAPU(keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "APU/Ver_APU";

    }

    @GetMapping("/Nuevo_APU")
    public String verFormularioDeRegistrarAPU(Model model) {
        model.addAttribute("apu", new APU());
        return "APU/Formulario_APU";

    }

    @PostMapping("/Guardar")
    public String guardarAPU(@ModelAttribute APU apu) {
        if (apu.getId() == null) {
            apu.setFechaCreacion(LocalDate.now());
        }
        if (apuService.guardarAPU(apu)) return "redirect:/APU?exitoGuardado";
        else return "redirect:/APU?errorGuardado";
    }

    @GetMapping("/{id}/Editar")
    public String verFormularioDeEditarAPU(@PathVariable Long id, Model model) {

        Optional<APU> optionalAPU = apuService.obtenerAPU(id);
        if (optionalAPU.isPresent()) {
            model.addAttribute("apu", optionalAPU.get());
            return "APU/Formulario_APU";
        } else return "redirect:/APU?errorAPU";
    }

    @GetMapping("/{id}/Eliminar")
    public String ocultarAPU(@PathVariable Long id, Model model) {

        if (apuService.ocultarAPU(id)) return "redirect:/APU?exitoOcultar";
        else return "redirect:/APU?errorOcultar";
    }


}