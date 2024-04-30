package com.ejm.springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "material_apu_item_subproyecto")
public class MaterialAPUItemSubproyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "apu_item_subproyecto_id")
    private APUItemSubproyecto apuItemSubproyecto;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    private int proveedor;
    private double valorUnitario;
    private double cantidad;
    private boolean enabled;
    @OneToMany(mappedBy = "materialAPUItemSubproyecto", cascade = CascadeType.ALL)
    private List<Transporte> transportes;

    public double getValorParcial() {
        double valorParcial = valorUnitario * cantidad;
        return Math.round(valorParcial * 100.0) / 100.0;
    }

}
