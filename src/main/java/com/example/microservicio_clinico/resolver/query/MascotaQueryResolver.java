package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.service.MascotaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class MascotaQueryResolver {
    
    private final MascotaService mascotaService;
    
    @DgsQuery
    public List<Mascota> mascotas() {
        return mascotaService.findAll();
    }
    
    @DgsQuery
    public Mascota mascota(@InputArgument Long id) {
        return mascotaService.findById(id)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
    }
    
    @DgsQuery
    public List<Mascota> mascotasPorCliente(@InputArgument Long clienteId) {
        return mascotaService.findByClienteId(clienteId);
    }
    
    @DgsQuery
    public List<Mascota> buscarMascotasPorNombre(@InputArgument String nombre) {
        return mascotaService.buscarPorNombre(nombre);
    }
    
    @DgsQuery
    public List<Mascota> buscarMascotasPorRaza(@InputArgument String raza) {
        return mascotaService.buscarPorRaza(raza);
    }
    
    @DgsQuery
    public List<Mascota> buscarMascotasPorColor(@InputArgument String color) {
        return mascotaService.buscarPorColor(color);
    }
    
    @DgsQuery
    public List<Mascota> buscarMascotasPorClienteYNombre(@InputArgument Long clienteId, @InputArgument String nombre) {
        return mascotaService.buscarPorClienteYNombre(clienteId, nombre);
    }
    
    @DgsQuery
    public Long contarMascotasPorCliente(@InputArgument Long clienteId) {
        return mascotaService.contarMascotasPorCliente(clienteId);
    }
}