package com.ejm.springboot.repository;

import com.ejm.springboot.entity.TokenRecovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRecoveryRepository extends JpaRepository<TokenRecovery, Long> {
    TokenRecovery findByToken(String token);
}
