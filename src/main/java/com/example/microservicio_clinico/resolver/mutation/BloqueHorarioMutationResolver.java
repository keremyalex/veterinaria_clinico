package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.BloqueHorarioInputDTO;
import com.example.microservicio_clinico.dto.BloqueHorarioUpdateDTO;
import com.example.microservicio_clinico.entity.BloqueHorario;
import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.service.BloqueHorarioService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class BloqueHorarioMutationResolver {
    
    private final BloqueHorarioService bloqueHorarioService;
    
    @DgsMutation
    public BloqueHorario crearBloqueHorario(@InputArgument BloqueHorarioInputDTO input) {
        BloqueHorario bloqueHorario = new BloqueHorario();
        bloqueHorario.setDia(input.getDia());
        bloqueHorario.setHoraInicio(input.getHoraInicio());
        bloqueHorario.setHoraFin(input.getHoraFin());
        
        // Establecer doctor
        Doctor doctor = new Doctor();
        doctor.setId(input.getDoctorId());
        bloqueHorario.setDoctor(doctor);
        
        return bloqueHorarioService.create(bloqueHorario);
    }
    
    @DgsMutation
    public BloqueHorario actualizarBloqueHorario(@InputArgument Long id, @InputArgument BloqueHorarioUpdateDTO input) {
        BloqueHorario bloqueHorario = new BloqueHorario();
        bloqueHorario.setDia(input.getDia());
        bloqueHorario.setHoraInicio(input.getHoraInicio());
        bloqueHorario.setHoraFin(input.getHoraFin());
        
        // Establecer doctor si se proporciona
        if (input.getDoctorId() != null) {
            Doctor doctor = new Doctor();
            doctor.setId(input.getDoctorId());
            bloqueHorario.setDoctor(doctor);
        }
        
        return bloqueHorarioService.update(id, bloqueHorario);
    }
    
    @DgsMutation
    public Boolean eliminarBloqueHorario(@InputArgument Long id) {
        try {
            bloqueHorarioService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}