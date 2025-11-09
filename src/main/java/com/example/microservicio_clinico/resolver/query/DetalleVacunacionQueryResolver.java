package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.DetalleVacunacion;
import com.example.microservicio_clinico.service.DetalleVacunacionService;
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
public class DetalleVacunacionQueryResolver {

    private final DetalleVacunacionService detalleVacunacionService;

    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacion() {
        log.info("Consultando todos los detalles de vacunación");
        return detalleVacunacionService.obtenerTodos();
    }

    @DgsQuery
    public DetalleVacunacion detalleVacunacion(@InputArgument String id) {
        log.info("Consultando detalle de vacunación con ID: {}", id);
        Optional<DetalleVacunacion> detalle = detalleVacunacionService.obtenerPorId(Long.parseLong(id));
        return detalle.orElse(null);
    }

    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorCarnet(@InputArgument String carnetVacunacionId) {
        log.info("Consultando detalles de vacunación del carnet ID: {}", carnetVacunacionId);
        return detalleVacunacionService.obtenerPorCarnetVacunacionId(Long.parseLong(carnetVacunacionId));
    }

    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorMascota(@InputArgument String mascotaId) {
        log.info("Consultando detalles de vacunación de la mascota ID: {}", mascotaId);
        return detalleVacunacionService.obtenerPorMascotaId(Long.parseLong(mascotaId));
    }

    @DgsQuery
    public List<DetalleVacunacion> detallesVacunacionPorVacuna(@InputArgument String vacunaId) {
        log.info("Consultando detalles de vacunación de la vacuna ID: {}", vacunaId);
        return detalleVacunacionService.obtenerPorVacunaId(Long.parseLong(vacunaId));
    }
}