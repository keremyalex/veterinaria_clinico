package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.MascotaInput;
import com.example.microservicio_clinico.dto.MascotaOutput;
import com.example.microservicio_clinico.dto.MascotaUpdateInput;
import com.example.microservicio_clinico.service.MascotaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class MascotaMutationResolver {
    
    private final MascotaService mascotaService;
    
    @DgsMutation
    public MascotaOutput crearMascota(@InputArgument MascotaInput input) {
        log.info("GraphQL Mutation: Creando mascota {} para cliente ID: {}", input.getNombre(), input.getClienteId());
        return mascotaService.crearMascota(input);
    }
    
    @DgsMutation
    public MascotaOutput actualizarMascota(@InputArgument MascotaUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando mascota con ID: {}", input.getId());
        return mascotaService.actualizarMascota(input);
    }
    
    @DgsMutation
    public Boolean eliminarMascota(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando mascota con ID: {}", id);
        return mascotaService.eliminarMascota(id);
    }
}