package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "diagnostico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnostico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column(nullable = false)
    private String observaciones;
    
    @Column(name = "fecharegistro", nullable = false)
    private LocalDateTime fecharegistro;
    
    // Relación muchos a uno con Cita
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;
    
    // Relación uno a muchos con Tratamiento
    @OneToMany(mappedBy = "diagnostico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tratamiento> tratamientos;
}