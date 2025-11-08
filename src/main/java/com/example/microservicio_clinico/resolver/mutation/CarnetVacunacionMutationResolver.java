package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.CarnetVacunacionInput;
import com.example.microservicio_clinico.dto.CarnetVacunacionOutput;
import com.example.microservicio_clinico.dto.CarnetVacunacionUpdateInput;
import com.example.microservicio_clinico.service.CarnetVacunacionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class CarnetVacunacionMutationResolver {
    
    private final CarnetVacunacionService carnetVacunacionService;
    
    @DgsMutation
    public CarnetVacunacionOutput crearCarnetVacunacion(@InputArgument CarnetVacunacionInput input) {
        log.info("GraphQL Mutation: Creando carnet de vacunación para mascota ID: {}", input.getMascotaId());
        return carnetVacunacionService.crearCarnetVacunacion(input);
    }
    
    @DgsMutation
    public CarnetVacunacionOutput actualizarCarnetVacunacion(@InputArgument CarnetVacunacionUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando carnet de vacunación con ID: {}", input.getId());
        return carnetVacunacionService.actualizarCarnetVacunacion(input);
    }
    
    @DgsMutation
    public Boolean eliminarCarnetVacunacion(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando carnet de vacunación con ID: {}", id);
        return carnetVacunacionService.eliminarCarnetVacunacion(id);
    }
}