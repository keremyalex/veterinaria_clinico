package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.service.DoctorService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class DoctorQueryResolver {

    private final DoctorService doctorService;

    @DgsQuery
    public List<Doctor> doctores() {
        log.info("Consultando todos los doctores");
        return doctorService.obtenerTodos();
    }

    @DgsQuery
    public Doctor doctor(@InputArgument String id) {
        log.info("Consultando doctor con ID: {}", id);
        Optional<Doctor> doctor = doctorService.obtenerPorId(Long.parseLong(id));
        return doctor.orElse(null);
    }

    @DgsQuery
    public Doctor doctorPorEmail(@InputArgument String email) {
        log.info("Consultando doctor con email: {}", email);
        Optional<Doctor> doctor = doctorService.obtenerPorEmail(email);
        return doctor.orElse(null);
    }
}