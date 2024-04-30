package com.ejm.springboot.controller;

import com.ejm.springboot.entity.Usuario;
import com.ejm.springboot.service.TokenRecoveryService;
import com.ejm.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Recuperar_Contrasena")
public class RecoveryController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    TokenRecoveryService tokenRecoveryService;

    @GetMapping
    public String mostrarFormularioDeRecuperarContraseña() {
        System.out.println("Waos");
        return "Recuperar_Contrasena";
    }

    @PostMapping("/Validar")
    public String validarCorreo(@RequestParam String correo) {
        System.out.println("waos2");
        Usuario usuario = usuarioService.obtenerUsuarioPorUsername(correo);
        if (usuario != null && usuario.isEnabled()) {
            tokenRecoveryService.crearToken(correo);
            return "redirect:/Recuperar_Contrasena?exito";
        }
        else return "redirect:/Recuperar_Contrasena?error";

    }

}
