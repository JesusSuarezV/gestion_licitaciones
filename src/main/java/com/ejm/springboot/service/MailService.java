package com.ejm.springboot.service;

import jakarta.mail.MessagingException;

public interface MailService {
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException;
}
