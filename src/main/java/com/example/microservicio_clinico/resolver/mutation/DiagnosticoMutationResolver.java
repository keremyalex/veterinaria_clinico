package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.DiagnosticoInputDTO;
import com.example.microservicio_clinico.dto.DiagnosticoUpdateDTO;
import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.entity.Diagnostico;
import com.example.microservicio_clinico.service.DiagnosticoService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class DiagnosticoMutationResolver {
    
    private final DiagnosticoService diagnosticoService;
    
    @DgsMutation
    public Diagnostico crearDiagnostico(@InputArgument DiagnosticoInputDTO input) {
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setEnfermedad(input.getEnfermedad());
        diagnostico.setDescripcion(input.getDescripcion());
        diagnostico.setSintomas(input.getSintomas());
        diagnostico.setMedicamentosRecetados(input.getMedicamentosRecetados());
        
        // Establecer cita
        Cita cita = new Cita();
        cita.setId(input.getCitaId());
        diagnostico.setCita(cita);
        
        return diagnosticoService.create(diagnostico);
    }
    
    @DgsMutation
    public Diagnostico actualizarDiagnostico(@InputArgument Long id, @InputArgument DiagnosticoUpdateDTO input) {
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setEnfermedad(input.getEnfermedad());
        diagnostico.setDescripcion(input.getDescripcion());
        diagnostico.setSintomas(input.getSintomas());
        diagnostico.setMedicamentosRecetados(input.getMedicamentosRecetados());
        
        return diagnosticoService.update(id, diagnostico);
    }
    
    @DgsMutation
    public Boolean eliminarDiagnostico(@InputArgument Long id) {
        try {
            diagnosticoService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}