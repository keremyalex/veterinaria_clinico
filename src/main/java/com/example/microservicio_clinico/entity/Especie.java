package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "especies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
    
    public Especie(String descripcion) {
        this.descripcion = descripcion;
    }
}