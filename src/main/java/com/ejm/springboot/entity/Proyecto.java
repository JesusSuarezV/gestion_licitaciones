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
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String ubicacion;

    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "creador_id")
    private Usuario creador;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "proyecto_usuario",
            joinColumns = @JoinColumn(name = "proyecto_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private List<Subproyecto> subproyectos = new ArrayList<>();

    private boolean enabled;

    public double getCosto(){
        double precio = 0.0;
        for(Subproyecto subproyecto: subproyectos){
            if(subproyecto.isEnabled()) precio+= subproyecto.getCosto();
        }
        return Math.round(precio*100d)/100d;
    }

}
