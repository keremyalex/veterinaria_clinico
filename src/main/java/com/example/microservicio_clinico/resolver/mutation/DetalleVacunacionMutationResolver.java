package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.DetalleVacunacionInput;
import com.example.microservicio_clinico.dto.DetalleVacunacionOutput;
import com.example.microservicio_clinico.dto.DetalleVacunacionUpdateInput;
import com.example.microservicio_clinico.service.DetalleVacunacionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class DetalleVacunacionMutationResolver {
    
    private final DetalleVacunacionService detalleVacunacionService;
    
    @DgsMutation
    public DetalleVacunacionOutput crearDetalleVacunacion(@InputArgument DetalleVacunacionInput input) {
        log.info("GraphQL Mutation: Creando detalle de vacunación para carnet ID: {} y vacuna ID: {}", 
                input.getCarnetVacunacionId(), input.getVacunaId());
        return detalleVacunacionService.crearDetalleVacunacion(input);
    }
    
    @DgsMutation
    public DetalleVacunacionOutput actualizarDetalleVacunacion(@InputArgument DetalleVacunacionUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando detalle de vacunación con ID: {}", input.getId());
        return detalleVacunacionService.actualizarDetalleVacunacion(input);
    }
    
    @DgsMutation
    public Boolean eliminarDetalleVacunacion(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando detalle de vacunación con ID: {}", id);
        return detalleVacunacionService.eliminarDetalleVacunacion(id);
    }
}