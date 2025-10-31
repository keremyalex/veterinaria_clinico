package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.service.MascotaService;
import com.example.microservicio_clinico.dto.MascotaInput;
import com.example.microservicio_clinico.dto.MascotaUpdateInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class MascotaMutationResolver {
    
    private final MascotaService mascotaService;
    
    @DgsMutation
    public Mascota createMascota(@InputArgument MascotaInput input) {
        return mascotaService.create(input);
    }
    
    @DgsMutation
    public Mascota updateMascota(@InputArgument MascotaUpdateInput input) {
        return mascotaService.update(input);
    }
    
    @DgsMutation
    public Boolean deleteMascota(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return mascotaService.delete(longId);
    }
}