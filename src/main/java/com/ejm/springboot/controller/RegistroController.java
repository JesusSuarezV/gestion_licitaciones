package com.ejm.springboot.controller;

import com.ejm.springboot.entity.Usuario;
import com.ejm.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/Registro")
    public String verFormularioDeRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "Registro";
    }

    @PostMapping("/Registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        if(!usuarioService.guardarUsuario(usuario)) return "redirect:/Registro?errorRegistro";
        else return "redirect:/Iniciar_Sesion?registro";
    }


}
