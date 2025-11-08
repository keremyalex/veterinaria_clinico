package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carnet_vacunacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarnetVacunacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "fechaemision", nullable = false)
    private LocalDateTime fechaemision;
    
    // Relación uno a uno con Mascota
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;
    
    // Relación uno a muchos con DetalleVacunacion
    @OneToMany(mappedBy = "carnetVacunacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleVacunacion> detallesVacunacion;
}