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
public class ItemSubproyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "subproyecto_id")
    private Subproyecto subproyecto;

    @OneToMany(mappedBy = "itemSubproyecto", cascade = CascadeType.ALL)
    private List<APUItemSubproyecto> apuItemSubproyectos = new ArrayList<>();

    private boolean enabled;

    public double getValorCapitulo(){
        double precio = 0.0;
        for(APUItemSubproyecto apuItemSubproyecto:apuItemSubproyectos){
           if(apuItemSubproyecto.isEnabled()) precio+= apuItemSubproyecto.getValorParcial();
        }
        return Math.round(precio*100d)/100d;
    }



}
