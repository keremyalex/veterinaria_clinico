package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.service.CitaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class CitaQueryResolver {

    private final CitaService citaService;

    @DgsQuery
    public List<Cita> citas() {
        log.info("Consultando todas las citas");
        return citaService.obtenerTodas();
    }

    @DgsQuery
    public Cita cita(@InputArgument String id) {
        log.info("Consultando cita con ID: {}", id);
        Optional<Cita> cita = citaService.obtenerPorId(Long.parseLong(id));
        return cita.orElse(null);
    }

    @DgsQuery
    public List<Cita> citasPorMascota(@InputArgument String mascotaId) {
        log.info("Consultando citas de la mascota ID: {}", mascotaId);
        return citaService.obtenerPorMascotaId(Long.parseLong(mascotaId));
    }

    @DgsQuery
    public List<Cita> citasPorDoctor(@InputArgument String doctorId) {
        log.info("Consultando citas del doctor ID: {}", doctorId);
        return citaService.obtenerPorDoctorId(Long.parseLong(doctorId));
    }

    @DgsQuery
    public List<Cita> citasPorEstado(@InputArgument Integer estado) {
        log.info("Consultando citas con estado: {}", estado);
        return citaService.obtenerPorEstado(estado);
    }

    @DgsQuery
    public List<Cita> citasPorFecha(@InputArgument String fecha) {
        log.info("Consultando citas para la fecha: {}", fecha);
        LocalDate localDate = LocalDate.parse(fecha);
        return citaService.obtenerPorFecha(localDate);
    }
}