package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.CitaInput;
import com.example.microservicio_clinico.dto.CitaOutput;
import com.example.microservicio_clinico.dto.CitaUpdateInput;
import com.example.microservicio_clinico.service.CitaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class CitaMutationResolver {
    
    private final CitaService citaService;
    
    @DgsMutation
    public CitaOutput crearCita(@InputArgument CitaInput input) {
        log.info("GraphQL Mutation: Creando cita para mascota ID: {} con doctor ID: {}", 
                input.getMascotaId(), input.getDoctorId());
        return citaService.crearCita(input);
    }
    
    @DgsMutation
    public CitaOutput actualizarCita(@InputArgument CitaUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando cita con ID: {}", input.getId());
        return citaService.actualizarCita(input);
    }
    
    @DgsMutation
    public Boolean eliminarCita(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando cita con ID: {}", id);
        return citaService.eliminarCita(id);
    }
}