package com.ejm.springboot.controller;

import com.ejm.springboot.entity.Material;
import com.ejm.springboot.entity.Usuario;
import com.ejm.springboot.service.MaterialService;
import com.ejm.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/Usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public String verMateriales(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("usuarios", usuarioService.buscarUsuario(keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "Usuarios/Ver_Usuarios";
    }


    @PostMapping("/Guardar_Rol")
    public String guardarRol(@RequestParam Long id, @RequestParam String role) {
        if (usuarioService.editarRol(id, role)) return "redirect:/Usuarios?exitoRol";
        else return "redirect:/Usuarios?errorRol";

    }

    @GetMapping("/{id}/Editar_Rol")
    public String verFormularioDeEditarRol(@PathVariable Long id, Model model) {
        Optional<Usuario> optionalUsuario = usuarioService.seleccionarUsuario(id);
        if (optionalUsuario.isPresent()) {
            model.addAttribute("id", id);
            model.addAttribute("roles", Arrays.asList("ADMIN", "EDITOR"));
            return "Usuarios/Formulario_Rol";
        } else {
            return "redirect:/Usuarios?errorUsuario";
        }
    }

    @GetMapping("/{id}/Bloquear")
    public String bloquearUsuario(@PathVariable Long id, Model model) {
        if (usuarioService.bloquearUsuario(id)){
            return "redirect:/Usuarios?exitoBloqueo";
        } else {
            return "redirect:/Usuarios?errorOcultar";
        }
    }

}
