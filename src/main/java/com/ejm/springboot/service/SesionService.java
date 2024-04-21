package com.ejm.springboot.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


public interface SesionService{

    public String getUsernameFromSession();

}
