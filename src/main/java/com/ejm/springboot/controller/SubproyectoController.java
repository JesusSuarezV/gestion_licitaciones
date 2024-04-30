package com.ejm.springboot.controller;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Subproyecto;
import com.ejm.springboot.service.ProyectoService;
import com.ejm.springboot.service.SesionService;
import com.ejm.springboot.service.SubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/Proyectos/{idProyecto}/Subproyectos")
public class SubproyectoController {

    @Autowired
    SubproyectoService subproyectoService;
    @Autowired
    ProyectoService proyectoService;

    @Autowired
    SesionService sesionService;

    @GetMapping
    public String verSubproyectos(Model model, @PathVariable Long idProyecto, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {
        Proyecto proyecto = proyectoService.obtenerProyecto(idProyecto).get();
        boolean owner = (proyecto.getCreador().getUsername().equals(sesionService.getUsernameFromSession()));
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("subproyectos", subproyectoService.buscarSubproyectos(keyword, idProyecto, PageRequest.of(page - 1, 3)));

        model.addAttribute("keyword", keyword);
        model.addAttribute("ruta", "");
        model.addAttribute("owner", owner);
        return "Subproyectos/Ver_Subproyectos";

    }
    @GetMapping("/Nuevo_Subproyecto")//URL
    public String verFormularioDeRegistrarSubproyectos(Model model, @PathVariable Long idProyecto) {
        model.addAttribute("proyecto", proyectoService.obtenerProyecto(idProyecto).get());
        model.addAttribute("subproyecto", new Subproyecto());
        return "Subproyectos/Formulario_Subproyecto";

    }

    @PostMapping("/Guardar")
    public String guardarProyecto(@PathVariable Long idProyecto,@ModelAttribute Subproyecto subproyecto){
        if (subproyecto.getId() == null) {
        subproyecto.setFechaCreacion(LocalDate.now());}
        subproyecto.setProyecto(proyectoService.obtenerProyecto(idProyecto).get());
        if (!subproyectoService.guardarSubproyecto(subproyecto)) return "redirect:/Proyectos/{idProyecto}/Subproyectos?error";
        else return "redirect:/Proyectos/{idProyecto}/Subproyectos";
    }

    @GetMapping("/{idSubproyecto}/Editar")//URL
    public String verFormularioDeEditarSubproyectos(@PathVariable Long idSubproyecto, Model model) {
        Optional<Subproyecto> optionalSubroyecto = subproyectoService.obtenerSubproyecto(idSubproyecto);
        if(optionalSubroyecto.isPresent()){
            model.addAttribute("subproyecto", optionalSubroyecto.get());
            model.addAttribute("proyecto", optionalSubroyecto.get().getProyecto());
            return "Subproyectos/Formulario_Subproyecto";}
        else return "redirect:/Subproyectos?errorProyecto";

    }

    @GetMapping("/{idSubproyecto}/Eliminar")//URL
    public String ocultarSubproyecto(@PathVariable Long idSubproyecto, Model model){
        if (!subproyectoService.ocultarProyecto(idSubproyecto)) return "redirect:/Proyectos/{idProyecto}/Subproyectos?errorOcultar";
        else return "redirect:/Proyectos/{idProyecto}/Subproyectos?exitoOcultar";
    }


}
