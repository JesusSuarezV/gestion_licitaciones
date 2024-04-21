package com.ejm.springboot.controller;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String verPaginaInicio(Model model){

        String password = "admin";

        // Genera un hash de la contraseña
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Imprime el hash generado
        System.out.println("Hash generado: " + hashedPassword);

        return "Index";
    }

    @GetMapping("/Iniciar_Sesion")
    public String verLogin(){
        return "Login";
    }

    @GetMapping("/logout")
    public String cerrarSesion(){
        return "redirect:/?logout";
    }
}
