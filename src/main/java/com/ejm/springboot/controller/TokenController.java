package com.ejm.springboot.controller;

import com.ejm.springboot.service.TokenRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TokenController {

    @Autowired
    TokenRegistroService tokenRegistroService;

    @GetMapping("/Confirmacion/{token}")
    public String confirmarCuenta(@PathVariable String token) {
        if (!tokenRegistroService.confirmarCuenta(token)) {
            System.out.println("No enctra");
            return "redirect:/Iniciar_Sesion?errorToken";}
        else{ return "redirect:/Iniciar_Sesion?exitoRegistro";}}


    }


