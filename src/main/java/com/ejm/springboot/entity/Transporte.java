package com.ejm.springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "material_apu_item_subproyecto_id")
    private MaterialAPUItemSubproyecto materialAPUItemSubproyecto;
    private double distancia;
    private double precio;
    private boolean enabled;

    public double getCantidadKM() {

        double cantidadKM = distancia * materialAPUItemSubproyecto.getCantidad();
        cantidadKM = Math.round(cantidadKM * 100d) / 100d;
        return cantidadKM;

    }

    public double getValorParcial() {

        double valorParcial = getCantidadKM() * precio;
        valorParcial = Math.round(valorParcial * 100d) / 100d;
        return valorParcial;

    }

}
