package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Tratamiento;
import com.example.microservicio_clinico.service.TratamientoService;
import com.example.microservicio_clinico.dto.TratamientoInput;
import com.example.microservicio_clinico.dto.TratamientoUpdateInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class TratamientoMutationResolver {
    
    private final TratamientoService tratamientoService;
    
    @DgsMutation
    public Tratamiento createTratamiento(@InputArgument TratamientoInput input) {
        return tratamientoService.create(input);
    }
    
    @DgsMutation
    public Tratamiento updateTratamiento(@InputArgument TratamientoUpdateInput input) {
        return tratamientoService.update(input);
    }
    
    @DgsMutation
    public Boolean deleteTratamiento(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return tratamientoService.delete(longId);
    }
}