package com.ejm.springboot.service;

import com.ejm.springboot.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemService {

    Page<Item> buscarItem(String nombre, Pageable pageable);

    boolean guardarItem(Item item);

    Optional<Item> obtenerItem(Long id);

    boolean ocultarItem(Long id);
}
