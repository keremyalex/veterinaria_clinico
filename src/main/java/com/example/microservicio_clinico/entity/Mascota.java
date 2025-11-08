package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "mascota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private Character sexo;
    
    @Column(nullable = false)
    private String raza;
    
    @Column(name = "fotourl")
    private String fotourl;
    
    @Column(name = "fechanacimiento", nullable = false)
    private LocalDate fechanacimiento;
    
    // Relación muchos a uno con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    // Relación muchos a uno con Especie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especie_id", nullable = false)
    private Especie especie;
    
    // Relación uno a muchos con Cita
    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cita> citas;
    
    // Relación uno a uno con CarnetVacunacion
    @OneToOne(mappedBy = "mascota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CarnetVacunacion carnetVacunacion;
}