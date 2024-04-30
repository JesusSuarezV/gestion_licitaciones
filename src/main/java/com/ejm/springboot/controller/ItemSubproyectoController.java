package com.ejm.springboot.controller;

import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Subproyecto;
import com.ejm.springboot.repository.ItemRepository;
import com.ejm.springboot.service.ItemService;
import com.ejm.springboot.service.ItemSubproyectoService;
import com.ejm.springboot.service.SubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Optional;

@Controller
@RequestMapping("/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items")
public class ItemSubproyectoController {
    @Autowired
    SubproyectoService subproyectoService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemSubproyectoService itemSubproyectoService;
    @GetMapping
    public String verItemsAsignados(Model model, @PathVariable Long idSubproyecto, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {

        model.addAttribute("subproyecto", subproyectoService.obtenerSubproyecto(idSubproyecto).get());
        model.addAttribute("items", itemSubproyectoService.buscarItemSubproyecto(idSubproyecto, keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        model.addAttribute("service", itemSubproyectoService);
        return "ItemSubproyectos/Ver_Items";

    }

    @GetMapping("/Agregar_Item")
    public String mostrarFormularioDeAsignarIems(@PathVariable Long idSubproyecto, Model model){
        Subproyecto subproyecto = subproyectoService.obtenerSubproyecto(idSubproyecto).get();
        model.addAttribute("subproyecto", subproyecto);
        model.addAttribute("items", itemRepository.findItemsNotInSubproyecto(subproyecto));

        return "ItemSubproyectos/Asignar_Item";
    }

    @PostMapping("/Asginar_Item")
    public String asignarItem(@PathVariable Long idSubproyecto, @RequestParam Long itemId){
        Subproyecto subproyecto = subproyectoService.obtenerSubproyecto(idSubproyecto).get();
        ItemSubproyecto itemSubproyecto = new ItemSubproyecto();
        itemSubproyecto.setSubproyecto(subproyecto);
        System.out.println("Aqui hay un error?");
        itemSubproyecto.setItem(itemService.obtenerItem(itemId).get());
        itemSubproyectoService.guardarItemSubproyecto(itemSubproyecto);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items";
    }

    @GetMapping("/{id}/Eliminar")
    public String ocultarItem(@PathVariable long id){

        itemSubproyectoService.ocultarItemSubproyecto(id);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items";
    }


}
