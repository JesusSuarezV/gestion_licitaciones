package com.ejm.springboot.controller;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.service.ProyectoService;
import com.ejm.springboot.service.SesionService;
import com.ejm.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private SesionService sesionService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/Mis_Proyectos")
    public String verMisProyectos(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") int page) {
        try {
            if (keyword == null || keyword == "") {


                model.addAttribute("proyectos", proyectoService.listarMisProyectos(sesionService.getUsernameFromSession(), PageRequest.of(page - 1, 3)));
                System.out.println("No estoy buscando");
            } else {
                model.addAttribute("proyectos", proyectoService.buscarMisProyectos(keyword, sesionService.getUsernameFromSession(), PageRequest.of(page - 1, 3)));
            }
            String ruta = "/Proyectos/Mis_Proyectos";
            model.addAttribute("keyword", keyword);
            model.addAttribute("ruta", ruta);
            return "Proyectos/Ver_Proyectos";
        } catch (Exception e) {
            return "Proyectos/Ver_Proyectos";
        }
    }

    @GetMapping
    public String verProyectos(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") int page) {
        try {
            if (keyword == null || keyword == "") {
                model.addAttribute("proyectos", proyectoService.listarProyectos(sesionService.getUsernameFromSession(), PageRequest.of(page - 1, 3)));
            } else {
                model.addAttribute("proyectos", proyectoService.buscarProyectos(keyword, sesionService.getUsernameFromSession(), PageRequest.of(page - 1, 3)));
            }
            String ruta = "/Proyectos";
            model.addAttribute("keyword", keyword);
            model.addAttribute("ruta", ruta);
            return "Proyectos/Ver_Proyectos";
        } catch (Exception e) {
            return "Proyectos/Ver_Proyectos";
        }
    }

    @GetMapping("/Nuevo_Proyecto")//URL
    public String verFormularioDeRegistrarProyectos(Model modelo) {

        modelo.addAttribute("proyecto", new Proyecto());
        return "Proyectos/Formulario_Proyecto";

    }

    @PostMapping("/Guardar")
    public String guardarProyecto(@ModelAttribute Proyecto proyecto) {

        if (proyecto.getId() == null) {
            System.out.println("Soy un proyecto nuevo");
            proyecto.setCreador(usuarioService.obtenerUsuarioPorUsername(sesionService.getUsernameFromSession()));
            proyecto.setFechaCreacion(LocalDate.now());
        } else System.out.println("Miento, soy un proyecto ya existente");
        if (!proyectoService.guardarProyecto(proyecto))
            return "redirect:/Proyectos/Mis_Proyectos?errorGuardado"; //URL en donde retorna el metodo
        else return "redirect:/Proyectos/Mis_Proyectos?exitoGuardado";
    }

    @GetMapping("/{id}/Editar")//URL
    public String verFormularioDeEditarProyectos(@PathVariable Long id, Model model) {
        Optional<Proyecto> optionalProyecto = proyectoService.obtenerProyecto(id);
        if (optionalProyecto.isPresent()) {
            model.addAttribute("proyecto", proyectoService.obtenerProyecto(id));

            model.addAttribute("edit", "edit");
            return "Proyectos/Formulario_Proyecto";
        } else return "redirect:/Proyectos?errorProyecto";

    }

    @GetMapping("/Actualizar")//URL
    public String actualizarProyecto(@ModelAttribute Proyecto proyecto) {

        proyectoService.guardarProyecto(proyecto);
        return "redirect:/Proyectos/Mis_Proyectos?Exito";

    }

    @GetMapping("/{id}/Eliminar")//URL
    public String ocultarProyecto(@PathVariable Long id, Model model) {

        if (!proyectoService.ocultarProyecto(id)) return "redirect:/Proyectos/Mis_Proyectos?errorOcultar";
        else return "redirect:/Proyectos/Mis_Proyectos?exitoOcultar";

    }

}
