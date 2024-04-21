package com.ejm.springboot.repository;

import com.ejm.springboot.entity.TokenRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRegistroRepository extends JpaRepository<TokenRegistro, String> {

    TokenRegistro findByToken(String token);

}
