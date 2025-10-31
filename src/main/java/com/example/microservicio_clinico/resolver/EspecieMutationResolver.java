package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Especie;
import com.example.microservicio_clinico.service.EspecieService;
import com.example.microservicio_clinico.dto.EspecieInput;
import com.example.microservicio_clinico.dto.EspecieUpdateInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class EspecieMutationResolver {
    
    private final EspecieService especieService;
    
    @DgsMutation
    public Especie createEspecie(@InputArgument EspecieInput input) {
        return especieService.create(input);
    }
    
    @DgsMutation
    public Especie updateEspecie(@InputArgument EspecieUpdateInput input) {
        return especieService.update(input);
    }
    
    @DgsMutation
    public Boolean deleteEspecie(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return especieService.delete(longId);
    }
}