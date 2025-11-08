package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.BloqueHorarioInput;
import com.example.microservicio_clinico.dto.BloqueHorarioOutput;
import com.example.microservicio_clinico.dto.BloqueHorarioUpdateInput;
import com.example.microservicio_clinico.service.BloqueHorarioService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class BloqueHorarioMutationResolver {
    
    private final BloqueHorarioService bloqueHorarioService;
    
    @DgsMutation
    public BloqueHorarioOutput crearBloqueHorario(@InputArgument BloqueHorarioInput input) {
        log.info("GraphQL Mutation: Creando bloque horario día semana: {}", input.getDiasemana());
        return bloqueHorarioService.crearBloqueHorario(input);
    }
    
    @DgsMutation
    public BloqueHorarioOutput actualizarBloqueHorario(@InputArgument BloqueHorarioUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando bloque horario con ID: {}", input.getId());
        return bloqueHorarioService.actualizarBloqueHorario(input);
    }
    
    @DgsMutation
    public Boolean eliminarBloqueHorario(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando bloque horario con ID: {}", id);
        return bloqueHorarioService.eliminarBloqueHorario(id);
    }
}