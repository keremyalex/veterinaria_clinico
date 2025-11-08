package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.DoctorInputDTO;
import com.example.microservicio_clinico.dto.DoctorUpdateDTO;
import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.service.DoctorService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class DoctorMutationResolver {
    
    private final DoctorService doctorService;
    
    @DgsMutation
    public Doctor crearDoctor(@InputArgument DoctorInputDTO input) {
        Doctor doctor = new Doctor();
        doctor.setNombre(input.getNombre());
        doctor.setApellido(input.getApellido());
        doctor.setCi(input.getCi());
        doctor.setTelefono(input.getTelefono());
        doctor.setEmail(input.getEmail());
        doctor.setFotocuf(input.getFotocuf());
        
        return doctorService.create(doctor);
    }
    
    @DgsMutation
    public Doctor actualizarDoctor(@InputArgument Long id, @InputArgument DoctorUpdateDTO input) {
        Doctor doctor = new Doctor();
        doctor.setNombre(input.getNombre());
        doctor.setApellido(input.getApellido());
        doctor.setCi(input.getCi());
        doctor.setTelefono(input.getTelefono());
        doctor.setEmail(input.getEmail());
        doctor.setFotocuf(input.getFotocuf());
        
        return doctorService.update(id, doctor);
    }
    
    @DgsMutation
    public Boolean eliminarDoctor(@InputArgument Long id) {
        try {
            doctorService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}