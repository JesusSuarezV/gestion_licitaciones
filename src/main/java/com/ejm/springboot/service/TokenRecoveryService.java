package com.ejm.springboot.service;

public interface TokenRecoveryService {

    boolean crearToken(String correo);

    boolean verificarToken(String token);

    boolean cambiarContraseña(String token, String nuevaContraseña);
}
