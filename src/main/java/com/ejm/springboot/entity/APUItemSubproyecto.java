package com.ejm.springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "apu_item_subproyecto")
public class APUItemSubproyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_subproyecto_id")
    private ItemSubproyecto itemSubproyecto;
    @ManyToOne
    @JoinColumn(name = "apu_id")
    private APU apu;

    @OneToMany(mappedBy = "apuItemSubproyecto", cascade = CascadeType.ALL)
    private List<MaterialAPUItemSubproyecto> materialAPUItemSubproyectoList = new ArrayList<>();


    @OneToMany(mappedBy = "apuItemSubproyecto", cascade = CascadeType.ALL)
    private List<MaquinariaAPUItemSubproyecto> maquinariaAPUItemSubproyectoList = new ArrayList<>();

    @OneToMany(mappedBy = "apuItemSubproyecto", cascade = CascadeType.ALL)
    private List<ManoObraAPUItemSubproyecto> manoObraAPUItemSubproyectoList = new ArrayList<>();

    private double cantidad;

    private boolean enabled;

    public double getPrecioMaterial(){
        double precio = 0.0;
        for(MaterialAPUItemSubproyecto materialAPUItemSubproyecto: materialAPUItemSubproyectoList){
            if(materialAPUItemSubproyecto.isEnabled()) precio += materialAPUItemSubproyecto.getValorParcial();
        }
        return precio;
    }

    public double getPrecioTransporte(){
        double precio = 0.0;
        for(MaterialAPUItemSubproyecto materialAPUItemSubproyecto: materialAPUItemSubproyectoList){
            if(materialAPUItemSubproyecto.isEnabled()){
                for(Transporte transporte:materialAPUItemSubproyecto.getTransportes()){
                    if(transporte.isEnabled()) precio+=transporte.getValorParcial();
                }
            }
        }

        return precio;
    }

    public double getPrecioMaquinaria(){
        double precio = 0.0;
        for(MaquinariaAPUItemSubproyecto maquinariaAPUItemSubproyecto : maquinariaAPUItemSubproyectoList){
            if(maquinariaAPUItemSubproyecto.isEnabled()) precio += maquinariaAPUItemSubproyecto.getValorParcial();
        }
        return precio;
    }
    public double getPrecioManoObra(){
        double precio = 0.0;
        for(ManoObraAPUItemSubproyecto manoObraAPUItemSubproyecto : manoObraAPUItemSubproyectoList){
            if(manoObraAPUItemSubproyecto.isEnabled()) precio += manoObraAPUItemSubproyecto.getValorParcial();
        }
        return precio;
    }

    public double getValorUnitario(){
        return getPrecioMaterial() + getPrecioTransporte() + getPrecioMaquinaria() + getPrecioManoObra();
    }

    public double getValorParcial(){
        return Math.round((getValorUnitario() * cantidad)*100d)/100d;
    }
}
