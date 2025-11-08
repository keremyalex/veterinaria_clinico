package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.TratamientoInputDTO;
import com.example.microservicio_clinico.dto.TratamientoUpdateDTO;
import com.example.microservicio_clinico.entity.Diagnostico;
import com.example.microservicio_clinico.entity.Tratamiento;
import com.example.microservicio_clinico.service.TratamientoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class TratamientoMutationResolver {
    
    private final TratamientoService tratamientoService;
    
    @DgsMutation
    public Tratamiento crearTratamiento(@InputArgument TratamientoInputDTO input) {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setMedicamento(input.getMedicamento());
        tratamiento.setDosis(input.getDosis());
        tratamiento.setFechaInicio(input.getFechaInicio());
        tratamiento.setFechaFin(input.getFechaFin());
        tratamiento.setObservaciones(input.getObservaciones());
        
        // Establecer diagnóstico
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setId(input.getDiagnosticoId());
        tratamiento.setDiagnostico(diagnostico);
        
        return tratamientoService.create(tratamiento);
    }
    
    @DgsMutation
    public Tratamiento actualizarTratamiento(@InputArgument Long id, @InputArgument TratamientoUpdateDTO input) {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setMedicamento(input.getMedicamento());
        tratamiento.setDosis(input.getDosis());
        tratamiento.setFechaInicio(input.getFechaInicio());
        tratamiento.setFechaFin(input.getFechaFin());
        tratamiento.setObservaciones(input.getObservaciones());
        
        return tratamientoService.update(id, tratamiento);
    }
    
    @DgsMutation
    public Boolean eliminarTratamiento(@InputArgument Long id) {
        try {
            tratamientoService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}