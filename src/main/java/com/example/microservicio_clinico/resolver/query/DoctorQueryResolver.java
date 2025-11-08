package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.service.DoctorService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@DgsComponent
@RequiredArgsConstructor
public class DoctorQueryResolver {
    
    private final DoctorService doctorService;
    
    @DgsQuery
    public List<Doctor> doctores() {
        return doctorService.findAll();
    }
    
    @DgsQuery
    public Doctor doctor(@InputArgument Long id) {
        return doctorService.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado con ID: " + id));
    }
    
    @DgsQuery
    public Doctor doctorPorEmail(@InputArgument String email) {
        return doctorService.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado con email: " + email));
    }
    
    @DgsQuery
    public Doctor doctorPorCi(@InputArgument String ci) {
        return doctorService.findByCi(ci)
            .orElseThrow(() -> new RuntimeException("Doctor no encontrado con CI: " + ci));
    }
    
    @DgsQuery
    public List<Doctor> buscarDoctoresPorNombre(@InputArgument String nombre) {
        return doctorService.buscarPorNombre(nombre);
    }
}