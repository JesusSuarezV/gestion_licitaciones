package com.ejm.springboot.controller;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ManoObra;
import com.ejm.springboot.entity.ManoObraAPUItemSubproyecto;
import com.ejm.springboot.entity.MaterialAPUItemSubproyecto;
import com.ejm.springboot.repository.ManoObraRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.ManoObraAPUItemSubproyectoService;
import com.ejm.springboot.service.ManoObraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/ManoObras")
public class ManoObraAPUItemSubproyectoController {

    @Autowired
    APUItemSubproyectoService apuItemSubproyectoService;

    @Autowired
    ManoObraAPUItemSubproyectoService manoObraAPUItemSubproyectoService;

    @Autowired
    ManoObraService manoObraService;
    @Autowired
    ManoObraRepository manoObraRepository;

    @GetMapping
    public String verManoObrasAsignadas(Model model, @PathVariable Long idAPUItemSubproyecto, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {


        model.addAttribute("apuItemSubproyecto", apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get());

        model.addAttribute("manoObras", manoObraAPUItemSubproyectoService.buscarManoObraAPUItemSubproyecto(idAPUItemSubproyecto, keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "ManoObraAPUItemSubproyectos/Ver_ManoObras";
    }

    @GetMapping("/Agregar_ManoObra")
    public String mostrarFormularioDeAsignarManoObra(@PathVariable Long idAPUItemSubproyecto, Model model) {
        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get();
        model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
        model.addAttribute("manoObras", manoObraRepository.findEnabledManoObraNotInManoObraAPUItemSubproyectoByAPUItemSubproyecto(apuItemSubproyecto));

        return "ManoObraAPUItemSubproyectos/Asignar_ManoObra";
    }

    @PostMapping("/Asignar_ManoObra")
    public String asignarManoObra(@PathVariable Long idAPUItemSubproyecto, @RequestParam Long manoObraId, @RequestParam double jornal, @RequestParam double prestacion,
                                  @RequestParam double rdto, @RequestParam double cantidad) {
        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get();
        ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto = new ManoObraAPUItemSubproyecto();
        manoObraAPUItemSubproyecto.setApuItemSubproyecto(apuItemSubproyecto);
        manoObraAPUItemSubproyecto.setJornal(jornal);
        manoObraAPUItemSubproyecto.setPrestacion(prestacion);
        manoObraAPUItemSubproyecto.setRdto(rdto);
        manoObraAPUItemSubproyecto.setCantidad(cantidad);
        manoObraAPUItemSubproyecto.setManoObra(manoObraService.obtenerManoObra(manoObraId).get());
        manoObraAPUItemSubproyectoService.guardarManoObraAPUItemSubproyecto(manoObraAPUItemSubproyecto);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/ManoObras";
    }

    @GetMapping("/{id}/Editar")
    public String mostrarFormularioDeEdicionDeLaManoObra(@PathVariable long id, Model model){

        ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto = manoObraAPUItemSubproyectoService.obtenerManoObraAPUItemSubproyecto(id).get();
        model.addAttribute("manoObraAPUItemSubproyecto", manoObraAPUItemSubproyecto);
        model.addAttribute("jornal", manoObraAPUItemSubproyecto.getJornal());
        model.addAttribute("prestacion", manoObraAPUItemSubproyecto.getPrestacion());
        model.addAttribute("rdto", manoObraAPUItemSubproyecto.getRdto());
        model.addAttribute("cantidad", manoObraAPUItemSubproyecto.getCantidad());

        return "ManoObraAPUItemSubproyectos/Editar_ManoObra";
    }
    @PostMapping("/{id}/Actualizar")
    public String actualizarManoObra(@PathVariable long id, @RequestParam double jornal, @RequestParam double prestacion, @RequestParam double rdto, @RequestParam double cantidad){

        ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto = manoObraAPUItemSubproyectoService.obtenerManoObraAPUItemSubproyecto(id).get();

        manoObraAPUItemSubproyecto.setJornal(jornal);
        manoObraAPUItemSubproyecto.setPrestacion(prestacion);
        manoObraAPUItemSubproyecto.setRdto(rdto);
        manoObraAPUItemSubproyecto.setCantidad(cantidad);
        manoObraAPUItemSubproyectoService.guardarManoObraAPUItemSubproyecto(manoObraAPUItemSubproyecto);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/ManoObras";
    }

    @GetMapping("/{id}/Eliminar")
    public String ocultarManoObra(@PathVariable long id){

        manoObraAPUItemSubproyectoService.ocultarManoObraAPUItemSubproyecto(id);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/ManoObras";
    }

}