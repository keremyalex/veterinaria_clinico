package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.CarnetVacunacion;
import com.example.microservicio_clinico.service.CarnetVacunacionService;
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
public class CarnetVacunacionQueryResolver {

    private final CarnetVacunacionService carnetVacunacionService;

    @DgsQuery
    public List<CarnetVacunacion> carnetsVacunacion() {
        log.info("Consultando todos los carnets de vacunación");
        return carnetVacunacionService.obtenerTodos();
    }

    @DgsQuery
    public CarnetVacunacion carnetVacunacion(@InputArgument String id) {
        log.info("Consultando carnet de vacunación con ID: {}", id);
        Optional<CarnetVacunacion> carnet = carnetVacunacionService.obtenerPorId(Long.parseLong(id));
        return carnet.orElse(null);
    }

    @DgsQuery
    public CarnetVacunacion carnetVacunacionPorMascota(@InputArgument String mascotaId) {
        log.info("Consultando carnet de vacunación de la mascota ID: {}", mascotaId);
        Optional<CarnetVacunacion> carnet = carnetVacunacionService.obtenerPorMascotaId(Long.parseLong(mascotaId));
        return carnet.orElse(null);
    }
}