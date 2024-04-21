package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.Item;
import com.ejm.springboot.repository.ItemRepository;
import com.ejm.springboot.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Page<Item> buscarItem(String nombre, Pageable pageable) {
        return itemRepository.findByEnabledTrueAndNombreContainingIgnoreCaseOrderByFechaCreacionDescNombreAsc(nombre, pageable);
    }

    @Override
    public boolean guardarItem(Item item) {
        try {
            item.setEnabled(true);
            itemRepository.save(item);
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<Item> obtenerItem(Long id) {
        return itemRepository.findByIdAndEnabledTrue(id);
    }

    @Override
    public boolean ocultarItem(Long id) {
        Optional<Item> optionalItem = itemRepository.findByIdAndEnabledTrue(id);
        if(optionalItem.isPresent()){
            Item item = optionalItem.get();
            item.setEnabled(false);
            itemRepository.save(item);
            return true;
        }
        else return false;
    }
}
