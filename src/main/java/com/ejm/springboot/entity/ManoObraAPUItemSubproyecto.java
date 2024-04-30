package com.ejm.springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mano_obra_apu_item_subproyecto")
public class ManoObraAPUItemSubproyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "apu_item_subproyecto_id")
    private APUItemSubproyecto apuItemSubproyecto;

    @ManyToOne
    @JoinColumn(name = "mano_obra_id")
    private ManoObra manoObra;

    private double jornal;
    private double prestacion;
    private double rdto;
    private double cantidad;
    private boolean enabled;

    public double getJornalTotal() {
        double jornalTotal = jornal + prestacion;
        jornalTotal = Math.round(jornalTotal * 100d)/100d;
        return jornalTotal;
    }

    public double getValorParcial() {

        double valorParcial = (cantidad *  getJornalTotal())/rdto;
        valorParcial = Math.round(valorParcial * 100d)/100d;
        return valorParcial;

    }

}
