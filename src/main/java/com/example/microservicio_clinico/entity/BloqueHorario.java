package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "bloque_horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloqueHorario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "diasemana", nullable = false)
    private Integer diasemana;
    
    @Column(name = "horainicio", nullable = false)
    private LocalTime horainicio;
    
    @Column(name = "horafinal", nullable = false)
    private LocalTime horafinal;
    
    @Column(nullable = false)
    private Integer activo;
    
    // Relación uno a muchos con Cita
    @OneToMany(mappedBy = "bloqueHorario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cita> citas;
}