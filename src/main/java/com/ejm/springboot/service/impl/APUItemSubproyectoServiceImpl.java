package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.*;
import com.ejm.springboot.repository.*;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.ItemSubproyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class APUItemSubproyectoServiceImpl implements APUItemSubproyectoService {

    @Autowired
    ItemSubproyectoService itemSubproyectoService;
    @Autowired
    APUItemSubproyectoRepository apuItemSubproyectoRepository;
    @Autowired
    MaterialAPUItemSubproyectoRepository materialAPUItemSubproyectoRepository;
    @Autowired
    TransporteRepository transporteRepository;

    @Autowired
    MaquinariaAPUItemSubproyectoRepository maquinariaAPUItemSubproyectoRepository;

    @Autowired
    ManoObraAPUItemSubproyectoRepository manoObraAPUItemSubproyectoRepository;

    @Override
    public Page<APUItemSubproyecto> buscarAPUItemSubproyecto(long idItemSubproyecto, String nombre, Pageable pageable) {
        Optional<ItemSubproyecto> optionalItemSubproyecto = itemSubproyectoService.obtenerItemSubproyecto(idItemSubproyecto);
        if (optionalItemSubproyecto.isPresent()) {
            ItemSubproyecto itemSubproyecto = optionalItemSubproyecto.get();
            return apuItemSubproyectoRepository.findByItemSubproyectoAndEnabledTrueAndApu_NombreContainingIgnoreCaseOrderByApu_NombreAsc(itemSubproyecto, nombre, pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public boolean guardarAPUItemSubproyecto(APUItemSubproyecto apuItemSubproyecto) {
        try {
            apuItemSubproyecto.setEnabled(true);
            apuItemSubproyectoRepository.save(apuItemSubproyecto);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<APUItemSubproyecto> obtenerAPUItemSubproyecto(Long id) {
        return apuItemSubproyectoRepository.findById(id);
    }

    @Override
    public boolean ocultarAPUItemSubproyecto(Long id) {
        Optional<APUItemSubproyecto> optionalAPUItemSubproyecto = apuItemSubproyectoRepository.findById(id);
        if (optionalAPUItemSubproyecto.isPresent()) {
            APUItemSubproyecto apuItemSubproyecto = optionalAPUItemSubproyecto.get();
            apuItemSubproyecto.setEnabled(false);
            apuItemSubproyectoRepository.save(apuItemSubproyecto);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double obtenerValorMateriales(APUItemSubproyecto apuItemSubproyecto) {
        double valorTotal = 0.0;
        List<MaterialAPUItemSubproyecto> materiales = materialAPUItemSubproyectoRepository.findByapuItemSubproyectoAndEnabledTrue(apuItemSubproyecto);
        for (MaterialAPUItemSubproyecto material : materiales) {

            valorTotal += material.getValorParcial();

        }

        return valorTotal;
    }

    @Override
    public double obtenerValorTransporte(APUItemSubproyecto apuItemSubproyecto) {
        double valorTotal = 0.0;
        List<Transporte> transportes = transporteRepository.findByAPUItemSubproyectoAndEnabledTrue(apuItemSubproyecto);
        for (Transporte transporte : transportes) {

            valorTotal += transporte.getValorParcial();

        }

        return valorTotal;
    }

    @Override
    public double obtenerValorMaquinaria(APUItemSubproyecto apuItemSubproyecto) {
        double valorTotal = 0.0;
        List<MaquinariaAPUItemSubproyecto> maquinarias = maquinariaAPUItemSubproyectoRepository.findByapuItemSubproyectoAndEnabledTrue(apuItemSubproyecto);
        for (MaquinariaAPUItemSubproyecto maquinaria : maquinarias) {

            valorTotal += maquinaria.getValorParcial();

        }

        return valorTotal;
    }

    @Override
    public double obtenerValorManoObra(APUItemSubproyecto apuItemSubproyecto) {
        double valorTotal = 0.0;
        List<ManoObraAPUItemSubproyecto> manoObras = manoObraAPUItemSubproyectoRepository.findByapuItemSubproyectoAndEnabledTrue(apuItemSubproyecto);
        for (ManoObraAPUItemSubproyecto manoObra : manoObras) {

            valorTotal += manoObra.getValorParcial();

        }

        return valorTotal;
    }

    @Override
    public double obtenerValorAPU(APUItemSubproyecto apuItemSubproyecto) {
        return obtenerValorMateriales(apuItemSubproyecto) + obtenerValorTransporte(apuItemSubproyecto) + obtenerValorMaquinaria(apuItemSubproyecto) + obtenerValorManoObra(apuItemSubproyecto);
    }

    @Override
    public double obtenerValorTotalAPU(APUItemSubproyecto apuItemSubproyecto) {
        return Math.round((obtenerValorAPU(apuItemSubproyecto) * apuItemSubproyecto.getCantidad())* 100d) / 100d;
    }

}
