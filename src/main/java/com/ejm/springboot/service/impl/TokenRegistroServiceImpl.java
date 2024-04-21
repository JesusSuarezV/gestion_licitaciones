package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.TokenRegistro;
import com.ejm.springboot.entity.Usuario;
import com.ejm.springboot.repository.TokenRegistroRepository;
import com.ejm.springboot.repository.UsuarioRepository;
import com.ejm.springboot.service.TokenRegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TokenRegistroServiceImpl implements TokenRegistroService {
    @Autowired
    TokenRegistroRepository tokenRegistroRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public boolean confirmarCuenta(String token) {
        TokenRegistro tokenRegistro = tokenRegistroRepository.findByToken(token);
        if (tokenRegistro != null && tokenRegistro.isEnabled() && (LocalDate.now().toEpochDay() - tokenRegistro.getFecha().toEpochDay())<15){

            Usuario usuario = tokenRegistro.getUsuario();
            usuario.setEnabled(true);
            usuarioRepository.save(usuario);
            tokenRegistro.setEnabled(false);
            tokenRegistroRepository.save(tokenRegistro);
            return true;
        }
        else return false;
    }
}
