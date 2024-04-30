package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.TokenRegistro;
import com.ejm.springboot.entity.Usuario;
import com.ejm.springboot.repository.TokenRegistroRepository;
import com.ejm.springboot.repository.UsuarioRepository;
import com.ejm.springboot.service.MailService;
import com.ejm.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRegistroRepository tokenRegistroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Override
    public Boolean guardarUsuario(Usuario usuario) {

        try {
            usuario.setRole("EDITOR");
            String contraseñaC = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(contraseñaC);
            usuarioRepository.save(usuario);
            String token = UUID.randomUUID().toString();
            TokenRegistro tokenRegistro = new TokenRegistro(token, LocalDate.now(), usuario, true);
            tokenRegistroRepository.save(tokenRegistro);
            String url = "https://gestionlicitaciones-production.up.railway.app/Confirmacion/" + token;
            String cuerpo = "Por favor, confirme su cuenta en el siguiente enlace: <a href=\"" + url + "\">" + url + "</a>";

            mailService.enviarCorreo(usuario.getUsername(), "ENLACE DE ACTIVACIÓN EJM", cuerpo);

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;

        }
    }

    @Override
    public Boolean cambiarContraseña(Usuario usuario, String password) {
        usuario.setPassword(password);
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    public Usuario obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Page<Usuario> buscarUsuario(String username, Pageable pageable) {
        return usuarioRepository.findByEnabledTrueAndUsernameContainingIgnoreCaseAndRoleNotOrderByUsernameAsc(username,"SADMIN", pageable);
    }

    @Override
    public Optional<Usuario> seleccionarUsuario(Long id) {
        return usuarioRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean editarRol(Long id, String role) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByIdAndEnabledTrue(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setRole(role);
            usuarioRepository.save(usuario);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean bloquearUsuario(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByIdAndEnabledTrue(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setEnabled(false);
            usuarioRepository.save(usuario);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Usuario> buscarEditor(Proyecto proyecto, String keyword, Pageable pageable) {
        return usuarioRepository.findByProyectosContainsAndEnabledTrueAndNombreContainingIgnoreCaseOrderByUsernameAsc(proyecto, keyword, pageable);
    }
}
