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
    private BigDecimal peso; // Agregando peso que vi en tu diagrama
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "especie_id", nullable = false)
    private Especie especie;
    
    public Mascota(String nombre, String sexo, String raza, LocalDate fechaNacimiento, 
                   BigDecimal peso, Cliente cliente, Especie especie) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.cliente = cliente;
        this.especie = especie;
    }
}