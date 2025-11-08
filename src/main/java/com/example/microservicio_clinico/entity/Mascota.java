package com.example.microservicio_clinico.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo; // 'M' para Macho, 'F' para Hembra
    
    @Column(name = "raza", nullable = false, length = 100)
    private String raza;
    
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;
    
    @Column(name = "color", length = 50)
    private String color;
    
    @Column(name = "foto_url", length = 500)
    private String fotourl;
    
    @Column(name = "observaciones", length = 1000)
    private String observaciones;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}