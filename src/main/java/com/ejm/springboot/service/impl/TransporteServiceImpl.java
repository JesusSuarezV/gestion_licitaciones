package com.ejm.springboot.service.impl;

import com.ejm.springboot.entity.APUItemSubproyecto;
import com.ejm.springboot.entity.Transporte;
import com.ejm.springboot.repository.TransporteRepository;
import com.ejm.springboot.service.APUItemSubproyectoService;
import com.ejm.springboot.service.TransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransporteServiceImpl implements TransporteService{

    @Autowired
    APUItemSubproyectoService apuItemSubproyectoService;
    @Autowired
    TransporteRepository transporteRepository; // Assuming you have a TransporteRepository

    @Override
    public Page<Transporte> buscarTransporte(long idAPUItemSubproyecto, String nombre, Pageable pageable) {
        Optional<APUItemSubproyecto> optionalAPUItemSubproyecto = apuItemSubproyectoService.obtenerAPUItemSubproyecto(idAPUItemSubproyecto);
        if (optionalAPUItemSubproyecto.isPresent()) {

            APUItemSubproyecto apuItemSubproyecto = optionalAPUItemSubproyecto.get();
            System.out.println(nombre + ": Waos");
            return transporteRepository.findByAPUItemSubproyectoAndVisibilidadTrueAndNombreMaterialContainingIgnoreCaseOrderByNombreAsc(apuItemSubproyecto, nombre, pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public boolean guardarTransporte(Transporte transporte) {
        try {
            transporte.setEnabled(true);
            transporteRepository.save(transporte);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public Optional<Transporte> obtenerTransporte(Long id) {
        return transporteRepository.findById(id);
    }

    @Override
    public boolean ocultarTransporte(Long id) {
        Optional<Transporte> optionalTransporte = transporteRepository.findById(id);
        if (optionalTransporte.isPresent()) {
            Transporte transporte = optionalTransporte.get();
            transporte.setEnabled(false);
            transporteRepository.save(transporte);
            return true;
        } else {
            return false;
        }
    }

}
