package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Horario;
import com.example.microservicio_clinico.service.HorarioService;
import com.example.microservicio_clinico.dto.HorarioInput;
import com.example.microservicio_clinico.dto.HorarioUpdateInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class HorarioMutationResolver {
    
    private final HorarioService horarioService;
    
    @DgsMutation
    public Horario createHorario(@InputArgument HorarioInput input) {
        return horarioService.create(input);
    }
    
    @DgsMutation
    public Horario updateHorario(@InputArgument HorarioUpdateInput input) {
        return horarioService.update(input);
    }
    
    @DgsMutation
    public Boolean deleteHorario(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return horarioService.delete(longId);
    }
}