package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.service.CitaService;
import com.example.microservicio_clinico.dto.CitaInput;
import com.example.microservicio_clinico.dto.CitaUpdateInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class CitaMutationResolver {
    
    private final CitaService citaService;
    
    @DgsMutation
    public Cita createCita(@InputArgument CitaInput input) {
        return citaService.create(input);
    }
    
    @DgsMutation
    public Cita updateCita(@InputArgument CitaUpdateInput input) {
        return citaService.update(input);
    }
    
    @DgsMutation
    public Boolean deleteCita(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return citaService.delete(longId);
    }
}