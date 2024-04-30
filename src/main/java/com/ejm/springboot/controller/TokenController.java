package com.ejm.springboot.controller;

import com.ejm.springboot.service.TokenRecoveryService;
import com.ejm.springboot.service.TokenRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TokenController {

    @Autowired
    TokenRegistroService tokenRegistroService;

    @Autowired
    TokenRecoveryService tokenRecoveryService;

    @GetMapping("/Confirmacion/{token}")
    public String confirmarCuenta(@PathVariable String token) {
        if (!tokenRegistroService.confirmarCuenta(token)) {
            System.out.println("No enctra");
            return "redirect:/?errorToken";
        } else {
            return "redirect:/?exitoRegistro";
        }
    }


    @GetMapping("/Recuperacion/{token}")
    public String mostrarFormularioDeCambiarContraseña(@PathVariable String token, Model model) {
        if (tokenRecoveryService.verificarToken(token)) {
            model.addAttribute("token", token);
            return "Recuperacion";
        } else {
            return "redirect:/Iniciar_Sesion?errorTokenRecovery";
        }
    }

    @PostMapping("/Recuperacion/{token}/Exito")
    public String cambiarContraseña(@PathVariable String token, @RequestParam String password) {
        if (tokenRecoveryService.verificarToken(token)) {
            System.out.println(password);
            tokenRecoveryService.cambiarContraseña(token, password);
            return "redirect:/?exitoCambio";
        } else {
            return "redirect:/?errorTokenRecovery";
        }
    }


}