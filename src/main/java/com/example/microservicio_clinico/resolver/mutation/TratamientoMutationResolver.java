package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.TratamientoInput;
import com.example.microservicio_clinico.dto.TratamientoOutput;
import com.example.microservicio_clinico.dto.TratamientoUpdateInput;
import com.example.microservicio_clinico.service.TratamientoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class TratamientoMutationResolver {
    
    private final TratamientoService tratamientoService;
    
    @DgsMutation
    public TratamientoOutput crearTratamiento(@InputArgument TratamientoInput input) {
        log.info("GraphQL Mutation: Creando tratamiento para diagnóstico ID: {}", input.getDiagnosticoId());
        return tratamientoService.crearTratamiento(input);
    }
    
    @DgsMutation
    public TratamientoOutput actualizarTratamiento(@InputArgument TratamientoUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando tratamiento con ID: {}", input.getId());
        return tratamientoService.actualizarTratamiento(input);
    }
    
    @DgsMutation
    public Boolean eliminarTratamiento(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando tratamiento con ID: {}", id);
        return tratamientoService.eliminarTratamiento(id);
    }
}