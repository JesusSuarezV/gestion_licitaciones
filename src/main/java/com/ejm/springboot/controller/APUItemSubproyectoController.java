package com.ejm.springboot.controller;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Subproyecto;
import com.ejm.springboot.repository.APURepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.APUService;
import com.ejm.springboot.service.ItemSubproyectoService;
import com.ejm.springboot.service.SubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU")
public class APUItemSubproyectoController {

    @Autowired
    APUItemSubproyectoService apuItemSubproyectoService;

    @Autowired
    ItemSubproyectoService itemSubproyectoService;

    @Autowired
    APURepository apuRepository;

    @Autowired
    APUService apuService;

    @GetMapping
    public String verAPUAsignados(Model model, @PathVariable Long idItemSubproyecto, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("itemSubproyecto", itemSubproyectoService.obtenerItemSubproyecto(idItemSubproyecto).get());
        model.addAttribute("apus", apuItemSubproyectoService.buscarAPUItemSubproyecto(idItemSubproyecto, keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        model.addAttribute("service", apuItemSubproyectoService);
        return "APUItemSubproyectos/Ver_APU";
    }

    @GetMapping("/Agregar_APU")
    public String mostrarFormularioDeAsignarAPU(@PathVariable Long idItemSubproyecto, Model model){
        ItemSubproyecto itemSubproyecto = itemSubproyectoService.obtenerItemSubproyecto(idItemSubproyecto).get();
        model.addAttribute("itemSubproyecto", itemSubproyecto);
        model.addAttribute("apus", apuRepository.findEnabledAPUNotInAPUItemSubproyectoByItemSubproyecto(itemSubproyecto));

        return "APUItemSubproyectos/Asignar_APU";
    }

    @PostMapping("/Asignar_APU")
    public String asignarAPU(@PathVariable Long idItemSubproyecto, @RequestParam Long apuId, @RequestParam double cantidad){
        ItemSubproyecto itemSubproyecto = itemSubproyectoService.obtenerItemSubproyecto(idItemSubproyecto).get();
        APUItemSubproyecto apuItemSubproyecto = new APUItemSubproyecto();
        apuItemSubproyecto.setItemSubproyecto(itemSubproyecto);
        apuItemSubproyecto.setApu(apuService.obtenerAPU(apuId).get());
        apuItemSubproyecto.setCantidad(cantidad);
        apuItemSubproyectoService.guardarAPUItemSubproyecto(apuItemSubproyecto);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU";
    }

    @GetMapping("/{id}/Editar")
    public String mostrarFormularioDeEdicionDelAPU(@PathVariable long id, Model model){

        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(id).get();
        model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
        model.addAttribute("cantidad", apuItemSubproyecto.getCantidad());
        return "APUItemSubproyectos/Editar_APU";
    }

    @PostMapping("/{id}/Actualizar")
    public String actualizarAPU(@PathVariable long id, @RequestParam double cantidad){

        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(id).get();
        apuItemSubproyecto.setCantidad(cantidad);
        apuItemSubproyectoService.guardarAPUItemSubproyecto(apuItemSubproyecto);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU";
    }

    @GetMapping("/{id}/Eliminar")
    public String ocultarAPU(@PathVariable long id){

        apuItemSubproyectoService.ocultarAPUItemSubproyecto(id);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU";
    }

    @GetMapping("/{id}/Recursos")
    public String mostrarInterfazDeRecursos(@PathVariable long id, Model model){
        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(id).get();
        model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
        return "Recursos/Recursos";
    }
}
