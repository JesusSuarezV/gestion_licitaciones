package com.ejm.springboot.controller;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Usuario;
import com.ejm.springboot.service.EditorService;
import com.ejm.springboot.service.ProyectoService;
import com.ejm.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/Proyectos/{id}/Editores")
public class EditorController {
    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String verEditores(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page, @PathVariable long id) {

        Optional<Proyecto> optionalProyecto = proyectoService.obtenerProyecto(id);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            model.addAttribute("proyecto", proyecto);
            model.addAttribute("editores", editorService.buscarEditor(proyecto, keyword, PageRequest.of(page - 1, 3)));
            model.addAttribute("keyword", keyword);
            return "Proyectos/Ver_Editores";
        }
        return "redirect:/Proyectos";
    }

    @GetMapping("/{idEditor}/Eliminar")
    public String eliminarEditor(@PathVariable long id, @PathVariable long idEditor) {
        Optional<Proyecto> optionalProyecto = proyectoService.obtenerProyecto(id);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            if (editorService.ocultarEditor(proyecto, idEditor))
                return "redirect:/Proyectos/{id}/Editores?exitoOcultar";

        }
        return "redirect:/Proyectos/{idProyecto}/Editores?errorOcultar";
    }

    @GetMapping("/Nuevo_Editor")
    public String verFormularioDeRegistrarEditor(Model model, @PathVariable long id) {
        Optional<Proyecto> optionalProyecto = proyectoService.obtenerProyecto(id);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            model.addAttribute("proyecto", proyecto);
            return "Proyectos/Formulario_Editor";
        }
        return "redirect:/Proyectos ";

    }

    @PostMapping("/Asignar_Editor")
    public String asignarEditor(@PathVariable long id, @RequestParam String correo) {
        System.out.println(correo);
        Optional<Proyecto> optionalProyecto = proyectoService.obtenerProyecto(id);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            if(editorService.asignarEditor(proyecto, correo)) return "redirect:/Proyectos/{id}/Editores?exitoEditor";
            else return "redirect:/Proyectos/{id}/Editores/Nuevo_Editor?errorCorreo";
        }
        return "redirect:/Proyectos ";
    }


}
