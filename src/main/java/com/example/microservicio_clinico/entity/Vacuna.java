package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "vacuna")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacuna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String descripcion;
    
    // Relación uno a muchos con DetalleVacunacion
    @OneToMany(mappedBy = "vacuna", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVacunacion> detallesVacunacion;
}