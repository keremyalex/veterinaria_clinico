package com.example.microservicio_clinico.resolver;

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
    public Mascota mascota(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return mascotaService.findById(longId)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
    }
    
    @DgsQuery
    public List<Mascota> mascotasByCliente(@InputArgument String clienteId) {
        Long longClienteId = Long.parseLong(clienteId);
        return mascotaService.findByClienteId(longClienteId);
    }
    
    @DgsQuery
    public List<Mascota> mascotasByEspecie(@InputArgument String especieId) {
        Long longEspecieId = Long.parseLong(especieId);
        return mascotaService.findByEspecieId(longEspecieId);
    }
    
    @DgsQuery
    public List<Mascota> searchMascotas(@InputArgument String searchTerm) {
        return mascotaService.searchByNombreOrRaza(searchTerm);
    }
    
    @DgsQuery
    public List<Mascota> mascotasBySexo(@InputArgument String sexo) {
        return mascotaService.findBySexo(sexo);
    }
}