package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.DiagnosticoInput;
import com.example.microservicio_clinico.dto.DiagnosticoOutput;
import com.example.microservicio_clinico.dto.DiagnosticoUpdateInput;
import com.example.microservicio_clinico.service.DiagnosticoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class DiagnosticoMutationResolver {
    
    private final DiagnosticoService diagnosticoService;
    
    @DgsMutation
    public DiagnosticoOutput crearDiagnostico(@InputArgument DiagnosticoInput input) {
        log.info("GraphQL Mutation: Creando diagnóstico para cita ID: {}", input.getCitaId());
        return diagnosticoService.crearDiagnostico(input);
    }
    
    @DgsMutation
    public DiagnosticoOutput actualizarDiagnostico(@InputArgument DiagnosticoUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando diagnóstico con ID: {}", input.getId());
        return diagnosticoService.actualizarDiagnostico(input);
    }
    
    @DgsMutation
    public Boolean eliminarDiagnostico(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando diagnóstico con ID: {}", id);
        return diagnosticoService.eliminarDiagnostico(id);
    }
}