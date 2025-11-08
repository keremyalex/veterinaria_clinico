package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.EspecieInput;
import com.example.microservicio_clinico.dto.EspecieOutput;
import com.example.microservicio_clinico.dto.EspecieUpdateInput;
import com.example.microservicio_clinico.service.EspecieService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class EspecieMutationResolver {
    
    private final EspecieService especieService;
    
    @DgsMutation
    public EspecieOutput crearEspecie(@InputArgument EspecieInput input) {
        log.info("GraphQL Mutation: Creando especie '{}'", input.getDescripcion());
        return especieService.crearEspecie(input);
    }
    
    @DgsMutation
    public EspecieOutput actualizarEspecie(@InputArgument EspecieUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando especie con ID: {}", input.getId());
        return especieService.actualizarEspecie(input);
    }
    
    @DgsMutation
    public Boolean eliminarEspecie(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando especie con ID: {}", id);
        return especieService.eliminarEspecie(id);
    }
}