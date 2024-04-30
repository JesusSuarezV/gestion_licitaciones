package com.ejm.springboot.controller;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.Maquinaria;
import com.ejm.springboot.entity.MaquinariaAPUItemSubproyecto;
import com.ejm.springboot.repository.MaquinariaRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.MaquinariaAPUItemSubproyectoService;
import com.ejm.springboot.service.MaquinariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Maquinarias")
public class MaquinariaAPUItemSubproyectoController {

    @Autowired
    APUItemSubproyectoService apuItemSubproyectoService;

    @Autowired
    MaquinariaAPUItemSubproyectoService maquinariaAPUItemSubproyectoService;

    @Autowired
    MaquinariaService maquinariaService;

    @Autowired
    MaquinariaRepository maquinariaRepository;

    @GetMapping
    public String verMaquinariaAsignada(Model model, @PathVariable Long idAPUItemSubproyecto, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {


        model.addAttribute("apuItemSubproyecto", apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get());

        model.addAttribute("maquinarias", maquinariaAPUItemSubproyectoService.buscarMaquinariaAPUItemSubproyecto(idAPUItemSubproyecto, keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "MaquinariaAPUItemSubproyectos/Ver_Maquinarias";
    }

    @GetMapping("/Agregar_Maquinaria")
    public String mostrarFormularioDeAsignarMaquinaria(@PathVariable Long idAPUItemSubproyecto, Model model) {
        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get();
        System.out.println(apuItemSubproyecto.getApu().getNombre());

        model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);

        List<Maquinaria> maquinarias = maquinariaRepository.findEnabledMaquinariaNotInMaquinariaAPUItemSubproyectoByAPUItemSubproyecto(apuItemSubproyecto);

        for (Maquinaria maquinaria:maquinarias){
            System.out.println(maquinaria.getNombre());
        }

        model.addAttribute("maquinarias", maquinariaRepository.findEnabledMaquinariaNotInMaquinariaAPUItemSubproyectoByAPUItemSubproyecto(apuItemSubproyecto));

        return "MaquinariaAPUItemSubproyectos/Asignar_Maquinaria"; // Update view template name
    }

    @PostMapping("/Asignar_Maquinaria")
    public String asignarMaquinaria(@PathVariable Long idAPUItemSubproyecto, @RequestParam Long maquinariaId, @RequestParam double tarifa, @RequestParam double rdto) {
        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get();
        MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto = new MaquinariaAPUItemSubproyecto();
        maquinariaAPUItemSubproyecto.setApuItemSubproyecto(apuItemSubproyecto);
        maquinariaAPUItemSubproyecto.setTarifa(tarifa);
        maquinariaAPUItemSubproyecto.setRdto(rdto);
        maquinariaAPUItemSubproyecto.setMaquinaria(maquinariaService.obtenerMaquinaria(maquinariaId).get());
        maquinariaAPUItemSubproyectoService.guardarMaquinariaAPUItemSubproyecto(maquinariaAPUItemSubproyecto);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Maquinarias";
    }

    @GetMapping("/{id}/Editar")
    public String mostrarFormularioDeEdicionDeLaMaquinaria(@PathVariable long id, Model model){

        MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto = maquinariaAPUItemSubproyectoService.obtenerMaquinariaAPUItemSubproyecto(id).get();
        model.addAttribute("maquinariaAPUItemSubproyecto", maquinariaAPUItemSubproyecto);
        model.addAttribute("tarifa", maquinariaAPUItemSubproyecto.getTarifa());
        model.addAttribute("rdto", maquinariaAPUItemSubproyecto.getRdto());
        return "MaquinariaAPUItemSubproyectos/Editar_Maquinaria"; // Update view template name
    }

    @PostMapping("/{id}/Actualizar")
    public String actualizarMaquinaria(@PathVariable long id, @RequestParam double tarifa, @RequestParam double rdto){

        MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto = maquinariaAPUItemSubproyectoService.obtenerMaquinariaAPUItemSubproyecto(id).get();
        maquinariaAPUItemSubproyecto.setTarifa(tarifa);
        maquinariaAPUItemSubproyecto.setRdto(rdto);
        maquinariaAPUItemSubproyectoService.guardarMaquinariaAPUItemSubproyecto(maquinariaAPUItemSubproyecto);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Maquinarias";
    }

    @GetMapping("/{id}/Eliminar")
    public String eliminarMaquinaria(@PathVariable long id){

        maquinariaAPUItemSubproyectoService.ocultarMaquinariaAPUItemSubproyecto(id);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Maquinarias";
    }}
