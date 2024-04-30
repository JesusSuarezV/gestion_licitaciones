package com.ejm.springboot.controller;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.MaterialAPUItemSubproyecto;
import com.ejm.springboot.repository.MaterialRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.MaterialAPUItemSubproyectoService;
import com.ejm.springboot.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Materiales")
public class MaterialAPUItemSubproyectoController {

    @Autowired
    APUItemSubproyectoService apuItemSubproyectoService;

    @Autowired
    MaterialAPUItemSubproyectoService materialAPUItemSubproyectoService;

    @Autowired
    MaterialService materialService;
    @Autowired
    MaterialRepository materialRepository;

    @GetMapping
    public String verMaterialesAsignados(Model model, @PathVariable Long idAPUItemSubproyecto, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) {


        model.addAttribute("apuItemSubproyecto", apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get());

        model.addAttribute("materiales", materialAPUItemSubproyectoService.buscarMaterialAPUItemSubproyecto(idAPUItemSubproyecto, keyword, PageRequest.of(page - 1, 3)));
        model.addAttribute("keyword", keyword);
        return "MaterialAPUItemSubproyectos/Ver_Materiales";
    }

    @GetMapping("/Agregar_Material")
    public String mostrarFormularioDeAsignarMaterial(@PathVariable Long idAPUItemSubproyecto, Model model) {
        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get();
        model.addAttribute("apuItemSubproyecto", apuItemSubproyecto);
        model.addAttribute("materiales", materialRepository.findEnabledMaterialNotInMaterialAPUItemSubproyectoByAPUItemSubproyecto(apuItemSubproyecto));

        return "MaterialAPUItemSubproyectos/Asignar_Material";
    }

    @PostMapping("/Asignar_Material")
    public String asignarMaterial(@PathVariable Long idAPUItemSubproyecto, @RequestParam Long materialId, @RequestParam int proveedor, @RequestParam double valorUnitario, @RequestParam double cantidad) {
        APUItemSubproyecto apuItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto).get();
        MaterialAPUItemSubproyecto materialAPUItemSubproyecto = new MaterialAPUItemSubproyecto();
        materialAPUItemSubproyecto.setApuItemSubproyecto(apuItemSubproyecto);
        materialAPUItemSubproyecto.setProveedor(proveedor);
        materialAPUItemSubproyecto.setValorUnitario(valorUnitario);
        materialAPUItemSubproyecto.setCantidad(cantidad);
        materialAPUItemSubproyecto.setMaterial(materialService.obtenerMaterial(materialId).get());
        materialAPUItemSubproyectoService.guardarMaterialAPUItemSubproyecto(materialAPUItemSubproyecto);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Materiales";
    }

    @GetMapping("/{id}/Editar")
    public String mostrarFormularioDeEdicionDelMaterial(@PathVariable long id, Model model){

        MaterialAPUItemSubproyecto materialAPUItemSubproyecto = materialAPUItemSubproyectoService.obtenerMaterialAPUItemSubproyecto(id).get();
        model.addAttribute("materialAPUItemSubproyecto", materialAPUItemSubproyecto);
        model.addAttribute("proveedor", materialAPUItemSubproyecto.getProveedor());
        model.addAttribute("valorUnitario", materialAPUItemSubproyecto.getValorUnitario());
        model.addAttribute("cantidad", materialAPUItemSubproyecto.getCantidad());
        return "MaterialAPUItemSubproyectos/Editar_Material";
    }

    @PostMapping("/{id}/Actualizar")
    public String actualizarMaterial(@PathVariable long id, @RequestParam int proveedor, @RequestParam double valorUnitario, @RequestParam double cantidad){

        MaterialAPUItemSubproyecto materialAPUItemSubproyecto = materialAPUItemSubproyectoService.obtenerMaterialAPUItemSubproyecto(id).get();
        materialAPUItemSubproyecto.setProveedor(proveedor);
        materialAPUItemSubproyecto.setValorUnitario(valorUnitario);
        materialAPUItemSubproyecto.setCantidad(cantidad);
        materialAPUItemSubproyectoService.guardarMaterialAPUItemSubproyecto(materialAPUItemSubproyecto);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Materiales";
    }
    @GetMapping("/{id}/Eliminar")
    public String ocultarMaterial(@PathVariable long id){

        materialAPUItemSubproyectoService.ocultarMaterialAPUItemSubproyecto(id);
        return "redirect:/Proyectos/{idProyecto}/Subproyectos/{idSubproyecto}/Items/{idItemSubproyecto}/APU/{idAPUItemSubproyecto}/Recursos/Materiales";
    }

}
