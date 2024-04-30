package com.ejm.springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "maquinaria_apu_item_subproyecto")
public class MaquinariaAPUItemSubproyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "apu_item_subproyecto_id")
    private APUItemSubproyecto apuItemSubproyecto;

    @ManyToOne
    @JoinColumn(name = "maquinaria_id")
    private Maquinaria maquinaria;

    private double tarifa;
    private double rdto;
    private boolean enabled;
    public double getValorParcial() {

        double valorParcial = tarifa / rdto;
        valorParcial = Math.round(valorParcial * 100d) / 100d;
        return valorParcial;

    }
}
