package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.entity.MascotaVacuna;
import com.example.microservicio_clinico.service.MascotaVacunaService;
import com.example.microservicio_clinico.dto.MascotaVacunaInput;
import com.example.microservicio_clinico.dto.MascotaVacunaUpdateInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class MascotaVacunaMutationResolver {
    
    private final MascotaVacunaService mascotaVacunaService;
    
    public MascotaVacunaMutationResolver(MascotaVacunaService mascotaVacunaService) {
        this.mascotaVacunaService = mascotaVacunaService;
    }
    
    @DgsMutation
    public MascotaVacuna aplicarVacuna(@InputArgument MascotaVacunaInput input) {
        return mascotaVacunaService.create(input);
    }
    
    @DgsMutation
    public MascotaVacuna updateMascotaVacuna(@InputArgument MascotaVacunaUpdateInput input) {
        return mascotaVacunaService.update(input);
    }
    
    @DgsMutation
    public Boolean deleteMascotaVacuna(@InputArgument String id) {
        return mascotaVacunaService.delete(Long.parseLong(id));
    }
}