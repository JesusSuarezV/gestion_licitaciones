package com.ejm.springboot.controller;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.ItemSubproyecto;
import com.ejm.springboot.entity.Proyecto;
import com.ejm.springboot.entity.Subproyecto;
import com.ejm.springboot.repository.APUItemSubproyectoRepository;
import com.ejm.springboot.repository.ItemSubproyectoRepository;
import com.ejm.springboot.repository.SubproyectoRepository;
import com.ejm.springboot.service.ProyectoService;
import com.ejm.springboot.service.SesionService;
import com.ejm.springboot.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private SesionService sesionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SubproyectoRepository subproyectoRepository;

    @Autowired
    private ItemSubproyectoRepository itemSubproyectoRepository;

    @Autowired
    private APUItemSubproyectoRepository apuItemSubproyectoRepository;

    @GetMapping("/Mis_Proyectos")
    public String verMisProyectos(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") int page) {
        try {
            if (keyword == null || keyword == "") {


                model.addAttribute("proyectos", proyectoService.listarMisProyectos(sesionService.getUsernameFromSession(), PageRequest.of(page - 1, 3)));
                System.out.println("No estoy buscando");
            } else {
                model.addAttribute("proyectos", proyectoService.buscarMisProyectos(keyword, sesionService.getUsernameFromSession(), PageRequest.of(page - 1, 3)));
            }
            String ruta = "/Proyectos/Mis_Proyectos";
            model.addAttribute("keyword", keyword);
            model.addAttribute("ruta", ruta);
            model.addAttribute("autor", true);

            return "Proyectos/Ver_Proyectos";
        } catch (Exception e) {
            return "Proyectos/Ver_Proyectos";
        }
    }

    @GetMapping
    public String verProyectos(Model model, @Param("keyword") String keyword, @RequestParam(defaultValue = "1") int page) {
        try {
            if (keyword == null || keyword == "") {
                model.addAttribute("proyectos", proyectoService.listarProyectos(sesionService.getUsernameFromSession(), PageRequest.of(page - 1, 3)));
            } else {
                model.addAttribute("proyectos", proyectoService.buscarProyectos(keyword, sesionService.getUsernameFromSession(), PageRequest.of(page - 1, 3)));
            }
            String ruta = "/Proyectos";
            model.addAttribute("keyword", keyword);
            model.addAttribute("ruta", ruta);
            return "Proyectos/Ver_Proyectos";
        } catch (Exception e) {
            return "Proyectos/Ver_Proyectos";
        }
    }

    @GetMapping("/Nuevo_Proyecto")//URL
    public String verFormularioDeRegistrarProyectos(Model modelo) {

        modelo.addAttribute("proyecto", new Proyecto());
        return "Proyectos/Formulario_Proyecto";

    }

    @PostMapping("/Guardar")
    public String guardarProyecto(@ModelAttribute Proyecto proyecto) {

        if (proyecto.getId() == null) {
            System.out.println("Soy un proyecto nuevo");
            proyecto.setCreador(usuarioService.obtenerUsuarioPorUsername(sesionService.getUsernameFromSession()));
            proyecto.setFechaCreacion(LocalDate.now());
        } else System.out.println("Miento, soy un proyecto ya existente");
        if (!proyectoService.guardarProyecto(proyecto))
            return "redirect:/Proyectos/Mis_Proyectos?errorGuardado"; //URL en donde retorna el metodo
        else return "redirect:/Proyectos/Mis_Proyectos?exitoGuardado";
    }

    @GetMapping("/{id}/Editar")//URL
    public String verFormularioDeEditarProyectos(@PathVariable Long id, Model model) {
        Optional<Proyecto> optionalProyecto = proyectoService.obtenerProyecto(id);

        if (optionalProyecto.isPresent()) {
            if (optionalProyecto.get().getCreador().getUsername().equals(sesionService.getUsernameFromSession())) {
                model.addAttribute("proyecto", proyectoService.obtenerProyecto(id));

                model.addAttribute("edit", "edit");
                return "Proyectos/Formulario_Proyecto";
            }
            else return "redirect:/Proyectos";
        } else return "redirect:/Proyectos?errorProyecto";

    }

    @GetMapping("/Actualizar")//URL
    public String actualizarProyecto(@ModelAttribute Proyecto proyecto) {

        proyectoService.guardarProyecto(proyecto);
        return "redirect:/Proyectos/Mis_Proyectos?Exito";

    }

    @GetMapping("/{id}/Eliminar")//URL
    public String ocultarProyecto(@PathVariable Long id, Model model) {
        if (proyectoService.obtenerProyecto(id).get().getCreador().getUsername().equals(sesionService.getUsernameFromSession())) {
            if (!proyectoService.ocultarProyecto(id)) return "redirect:/Proyectos/Mis_Proyectos?errorOcultar";
            else return "redirect:/Proyectos/Mis_Proyectos?exitoOcultar";
        }
        else return "redirect:/Proyectos";
    }

    @GetMapping("/{id}/Visualizar")
    public String visualizarProyecto(@PathVariable Long id, Model model) {
        Proyecto proyecto = proyectoService.obtenerProyecto(id).get();
        String htmlContent = "";
        model.addAttribute("proyecto", proyecto);
        List<Subproyecto> subproyectos = subproyectoRepository.findByProyectoAndEnabledTrueOrderByNombreAsc(proyecto);
        double contadorItem = 0;
        double contadorAPU = 0;


        for (Subproyecto subproyecto : subproyectos) {
            htmlContent += "<tr><td colspan='7'><strong>" + subproyecto.getNombre() + "</strong></td></tr>";
            List<ItemSubproyecto> itemSubproyectos = itemSubproyectoRepository.findBySubproyectoAndEnabledTrueOrderByItem_NombreAsc(subproyecto);
            contadorItem = 0;
            for (ItemSubproyecto itemSubproyecto : itemSubproyectos) {
                contadorItem++;
                htmlContent += "<tr><td><strong>" + contadorItem + "</strong></td><td><strong>" + itemSubproyecto.getItem().getNombre() + "</strong></td><td></td><td></td><td></td><td></td><td><strong>$ " + itemSubproyecto.getValorCapitulo() + "</strong></td></tr>";
                List<APUItemSubproyecto> apuItemSubproyectos = apuItemSubproyectoRepository.findByItemSubproyectoAndEnabledTrueOrderByApu_NombreAsc(itemSubproyecto);
                contadorAPU = 0;
                for (APUItemSubproyecto apuItemSubproyecto : apuItemSubproyectos) {
                    contadorAPU++;
                    htmlContent += "<tr><td><strong>" + (int) contadorItem + "." + (int) contadorAPU + "</strong></td><td>" + apuItemSubproyecto.getApu().getNombre() + "</td><td>" + apuItemSubproyecto.getApu().getUnidad() + "</td><td>" + Math.round(apuItemSubproyecto.getCantidad() * 100d) / 100d + "</td><td>$ " + apuItemSubproyecto.getValorUnitario() + "</td><td>$ " + apuItemSubproyecto.getValorParcial() + "</td><td></td></tr>";

                }
            }
            htmlContent += "<tr><td></td><td class='texto-izquierda' colspan = '5'><strong>SUBTOTAL " + subproyecto.getNombre() + "</strong></td><td><strong>$ " + subproyecto.getCosto() + "</strong></td></tr>";
        }
        htmlContent += "<tr><td></td><td class='texto-izquierda' colspan = '5'><strong>COSTO TOTAL DEL PROYECTO</strong></td><td><strong>$ " + proyecto.getCosto() + "</strong></td></tr>";
        model.addAttribute("htmlContent", htmlContent);

        return "Proyectos/Visualizar_Proyecto";

    }

}
