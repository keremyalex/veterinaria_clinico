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
    
    // Relación ManyToOne con Cliente (Un cliente puede tener muchas citas)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    // Relación ManyToOne con Horario (Un horario puede tener muchas citas)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "horario_id", nullable = false)
    private Horario horario;
    
    // Relación ManyToOne con Mascota (Una mascota puede tener muchas citas)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;
}