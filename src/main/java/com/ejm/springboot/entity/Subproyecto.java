package com.ejm.springboot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subproyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
    private LocalDate fechaCreacion;
    private boolean enabled;
    @OneToMany(mappedBy = "subproyecto", cascade = CascadeType.ALL)
    private List<ItemSubproyecto> itemSubproyectos = new ArrayList<>();

    public double getCosto(){
        double precio = 0.0;
        for(ItemSubproyecto itemSubproyecto: itemSubproyectos){
            if(itemSubproyecto.isEnabled()) precio+= itemSubproyecto.getValorCapitulo();
        }
        return Math.round(precio*100d)/100d;
    }
}
