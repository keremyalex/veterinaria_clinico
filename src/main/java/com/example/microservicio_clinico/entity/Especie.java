package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "especie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String descripcion;
    
    // Relación uno a muchos con Mascota
    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mascota> mascotas;
}