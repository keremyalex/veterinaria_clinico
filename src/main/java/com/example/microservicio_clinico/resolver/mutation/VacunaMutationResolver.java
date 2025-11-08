package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.VacunaInput;
import com.example.microservicio_clinico.dto.VacunaOutput;
import com.example.microservicio_clinico.dto.VacunaUpdateInput;
import com.example.microservicio_clinico.service.VacunaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class VacunaMutationResolver {
    
    private final VacunaService vacunaService;
    
    @DgsMutation
    public VacunaOutput crearVacuna(@InputArgument VacunaInput input) {
        log.info("GraphQL Mutation: Creando vacuna '{}'", input.getDescripcion());
        return vacunaService.crearVacuna(input);
    }
    
    @DgsMutation
    public VacunaOutput actualizarVacuna(@InputArgument VacunaUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando vacuna con ID: {}", input.getId());
        return vacunaService.actualizarVacuna(input);
    }
    
    @DgsMutation
    public Boolean eliminarVacuna(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando vacuna con ID: {}", id);
        return vacunaService.eliminarVacuna(id);
    }
}