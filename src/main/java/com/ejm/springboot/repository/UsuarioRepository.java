package com.ejm.springboot.repository;

import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String Username);

    Page<Usuario> findByEnabledTrueAndUsernameContainingIgnoreCaseAndRoleNotOrderByUsernameAsc(String username,String role, Pageable pageable);

    Optional<Usuario> findByIdAndEnabledTrue(Long id);

    Page<Usuario> findByProyectosContainsAndEnabledTrueAndNombreContainingIgnoreCaseOrderByUsernameAsc(Proyecto proyecto, String nombre, Pageable pageable);

}
