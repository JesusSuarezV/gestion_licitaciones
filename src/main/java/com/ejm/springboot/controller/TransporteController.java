package com.ejm.springboot.controller;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.MaterialAPUItemSubproyecto;
import com.ejm.springboot.entity.Transporte;
import com.ejm.springboot.repository.MaterialAPUItemSubproyectoRepository;
import com.ejm.springboot.repository.MaterialRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.MaterialAPUItemSubproyectoService;
import com.ejm.springboot.service.TransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Transportes")
public class TransporteController {
    @Autowired
    APUItemSubproyectoService apuItemSubproyectoService;

    @Autowired
    TransporteService transporteService;

    @Autowired
    MaterialAPUItemSubproyectoService materialAPUItemSubproyectoService;

    @Autowired
    MaterialAPUItemSubproyectoRepository materialAPUItemSubproyectoRepository;

    @GetMapping
    public String verTransportesAsignados(Model model, @PathVariable Long idAPUItemSubproyecto, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {


        model.addAttribute("apuItemSubproyecto", apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get());
        model.addAttribute("transportes", transporteService.buscarTransporte(idAPUItemSubproyecto, keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "Transporte/Ver_Transportes";
    }

    @GetMapping("/Agregar_Transporte")
    public String mostrarFormularioDeAsignarTransporte(@PathVariable Long idAPUItemSubproyecto, Model model) {
        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get();
        model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);

        model.addAttribute("materiales", materialAPUItemSubproyectoRepository.findMaterialesAsignadosNotInTransporte(apuItemSubproyecto));

        return "Transporte/Asignar_Transporte";
    }

    @PostMapping("/Asignar_Transporte")
    public String asignarTransporte(@PathVariable Long idAPUItemSubproyecto, @RequestParam Long materialAPUItemSubproyectoId, @RequestParam double distancia, @RequestParam double precio) {
        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get();
        Transporte transporte = new Transporte();
        transporte.setDistancia(distancia);
        transporte.setPrecio(precio);
        transporte.setMaterialAPUItemSubproyecto(materialAPUItemSubproyectoService.obtenerMaterialAPUItemSubproyecto(materialAPUItemSubproyectoId).get());
        transporteService.guardarTransporte(transporte);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Transportes";
    }
    @GetMapping("/{id}/Editar")
    public String mostrarFormularioDeEdicionDelTransporte(@PathVariable long id, Model model){

        Transporte transporte = transporteService.obtenerTransporte(id).get();
        model.addAttribute("transporte", transporte);
        model.addAttribute("distancia", transporte.getDistancia());
        model.addAttribute("precio", transporte.getPrecio());
        return "Transporte/Editar_Transporte";
    }

    @PostMapping("/{id}/Actualizar")
    public String actualizarTransporte(@PathVariable long id, @RequestParam double distancia, @RequestParam double precio){

        Transporte transporte = transporteService.obtenerTransporte(id).get();
        transporte.setDistancia(distancia);
        transporte.setPrecio(precio);
        transporteService.guardarTransporte(transporte);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Transportes";
    }

    @GetMapping("/{id}/Eliminar")
    public String ocultarTransporte(@PathVariable long id){

        transporteService.ocultarTransporte(id);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Transportes";
    }
}
