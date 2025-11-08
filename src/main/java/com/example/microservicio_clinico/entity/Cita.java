package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fecha_reservacion", nullable = false)
    private LocalDateTime fechaReservacion;
    
    @Column(nullable = false)
    private String motivo;
    
    @Column(name = "fecha_programada", nullable = false) 
    private LocalDateTime fechaProgramada;
    
    @Column(name = "estado", length = 50)
    private String estado; // PROGRAMADA, EN_CURSO, FINALIZADA, CANCELADA
    
    @Column(name = "observaciones", length = 1000)
    private String observaciones;
    
    // Relación ManyToOne con Cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    // Relación ManyToOne con Doctor
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    // Relación ManyToOne con Mascota
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;
}