package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Diagnostico;
import com.example.microservicio_clinico.service.DiagnosticoService;
import com.example.microservicio_clinico.dto.DiagnosticoInput;
import com.example.microservicio_clinico.dto.DiagnosticoUpdateInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class DiagnosticoMutationResolver {
    
    private final DiagnosticoService diagnosticoService;
    
    @DgsMutation
    public Diagnostico createDiagnostico(@InputArgument DiagnosticoInput input) {
        return diagnosticoService.create(input);
    }
    
    @DgsMutation
    public Diagnostico updateDiagnostico(@InputArgument DiagnosticoUpdateInput input) {
        return diagnosticoService.update(input);
    }
    
    @DgsMutation
    public Boolean deleteDiagnostico(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return diagnosticoService.delete(longId);
    }
}