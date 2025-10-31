package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.entity.Vacuna;
import com.example.microservicio_clinico.service.VacunaService;
import com.example.microservicio_clinico.dto.VacunaInput;
import com.example.microservicio_clinico.dto.VacunaUpdateInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class VacunaMutationResolver {
    
    private final VacunaService vacunaService;
    
    public VacunaMutationResolver(VacunaService vacunaService) {
        this.vacunaService = vacunaService;
    }
    
    @DgsMutation
    public Vacuna createVacuna(@InputArgument VacunaInput input) {
        return vacunaService.create(input);
    }
    
    @DgsMutation
    public Vacuna updateVacuna(@InputArgument VacunaUpdateInput input) {
        return vacunaService.update(input);
    }
    
    @DgsMutation
    public Boolean deleteVacuna(@InputArgument String id) {
        return vacunaService.delete(Long.parseLong(id));
    }
}