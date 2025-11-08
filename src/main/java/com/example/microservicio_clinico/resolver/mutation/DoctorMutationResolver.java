package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.DoctorInput;
import com.example.microservicio_clinico.dto.DoctorOutput;
import com.example.microservicio_clinico.dto.DoctorUpdateInput;
import com.example.microservicio_clinico.service.DoctorService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class DoctorMutationResolver {
    
    private final DoctorService doctorService;
    
    @DgsMutation
    public DoctorOutput crearDoctor(@InputArgument DoctorInput input) {
        log.info("GraphQL Mutation: Creando doctor con CI: {}", input.getCi());
        return doctorService.crearDoctor(input);
    }
    
    @DgsMutation
    public DoctorOutput actualizarDoctor(@InputArgument DoctorUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando doctor con ID: {}", input.getId());
        return doctorService.actualizarDoctor(input);
    }
    
    @DgsMutation
    public Boolean eliminarDoctor(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando doctor con ID: {}", id);
        return doctorService.eliminarDoctor(id);
    }
}