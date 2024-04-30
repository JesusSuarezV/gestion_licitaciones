package com.ejm.springboot.controller;

import com.ejm.springboot.entity.Item;
import com.ejm.springboot.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/Items")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping
    public String verItems(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {

        model.addAttribute("items", itemService.buscarItem(keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "Items/Ver_Items";

    }

    @GetMapping("/Nuevo_Item")
    public String verFormularioDeRegistrarItem(Model model) {
        model.addAttribute("item", new Item());
        return "Items/Formulario_Item";

    }

    @PostMapping("/Guardar")
    public String guardarItem(@ModelAttribute Item item) {
        if (item.getId() == null) {
            item.setFechaCreacion(LocalDate.now());
        }
        if (itemService.guardarItem(item)) return "redirect:/Items?exitoGuardado";
        else return "redirect:/Items?errorGuardado";
    }

    @GetMapping("/{id}/Editar")
    public String verFormularioDeEditarItem(@PathVariable Long id, Model model) {

        Optional<Item> optionalItem = itemService.obtenerItem(id);
        if (optionalItem.isPresent()) {
            model.addAttribute("item", optionalItem.get());
            return "Items/Formulario_Item";
        } else return "redirect:/Items?errorItem";
    }

    @GetMapping("/{id}/Eliminar")
    public String ocultarItem(@PathVariable Long id, Model model) {

        if (itemService.ocultarItem(id)) return "redirect:/Items?exitoOcultar";
        else return "redirect:/Items?errorOcultar";
    }


}
