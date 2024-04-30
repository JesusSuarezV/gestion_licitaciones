package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.TokenRecovery;
import com.ejm.springboot.entity.TokenRegistro;
import com.ejm.springboot.entity.Usuario;
import com.ejm.springboot.repository.TokenRecoveryRepository;
import com.ejm.springboot.repository.TokenRegistroRepository;
import com.ejm.springboot.repository.UsuarioRepository;
import com.ejm.springboot.service.MailService;
import com.ejm.springboot.service.TokenRecoveryService;
import com.ejm.springboot.service.TokenRegistroService;
import com.ejm.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TokenRecoveryServiceImpl implements TokenRecoveryService {
    @Autowired
    TokenRecoveryRepository tokenRecoveryRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Override
    public boolean crearToken(String correo) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorUsername(correo);
            String token = UUID.randomUUID().toString();
            TokenRecovery tokenRecovery = new TokenRecovery(token,LocalDate.now(), usuario, true);
            tokenRecoveryRepository.save(tokenRecovery);

            String url = "https://gestionlicitaciones-production.up.railway.app/Recuperacion/" + token;
            String cuerpo = "Por favor, cambie su contraseña en el siguiente enlace: <a href=\"" + url + "\">" + url + "</a>";

            mailService.enviarCorreo(usuario.getUsername(), "CAMBIAR CONTRASEÑA EJM", cuerpo);

            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean verificarToken(String token) {
        TokenRecovery tokenRecovery = tokenRecoveryRepository.findByToken(token);
        if (tokenRecovery != null && tokenRecovery.isEnabled() && (LocalDate.now().toEpochDay() - tokenRecovery.getFecha().toEpochDay()) < 15) {
            return true;
        } else return false;
    }

    @Override
    public boolean cambiarContraseña(String token, String nuevaContraseña) {
        TokenRecovery tokenRecovery = tokenRecoveryRepository.findByToken(token);
        if (tokenRecovery != null && tokenRecovery.isEnabled() && (LocalDate.now().toEpochDay() - tokenRecovery.getFecha().toEpochDay()) < 15) {
            System.out.println("MiContraseña es:" + nuevaContraseña);
            String nuevaContraseñaEncriptada = passwordEncoder.encode(nuevaContraseña);
            System.out.println("Y Encriptada es:" + nuevaContraseñaEncriptada);
            usuarioService.cambiarContraseña(tokenRecovery.getUsuario(),nuevaContraseñaEncriptada);
            tokenRecovery.setEnabled(false);
            tokenRecoveryRepository.save(tokenRecovery);

            return true;
        } else return false;

    }
}
