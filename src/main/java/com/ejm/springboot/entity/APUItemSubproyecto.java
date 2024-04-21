package com.ejm.springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private double cantidad;

    private boolean enabled;
}
