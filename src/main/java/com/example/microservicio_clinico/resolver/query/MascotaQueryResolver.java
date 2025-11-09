package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.service.MascotaService;
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
public class MascotaQueryResolver {

    private final MascotaService mascotaService;

    @DgsQuery
    public List<Mascota> mascotas() {
        log.info("Consultando todas las mascotas");
        return mascotaService.obtenerTodas();
    }

    @DgsQuery
    public Mascota mascota(@InputArgument String id) {
        log.info("Consultando mascota con ID: {}", id);
        Optional<Mascota> mascota = mascotaService.obtenerPorId(Long.parseLong(id));
        return mascota.orElse(null);
    }

    @DgsQuery
    public List<Mascota> mascotasPorCliente(@InputArgument String clienteId) {
        log.info("Consultando mascotas del cliente ID: {}", clienteId);
        return mascotaService.obtenerPorClienteId(Long.parseLong(clienteId));
    }

    @DgsQuery
    public List<Mascota> mascotasPorEspecie(@InputArgument String especieId) {
        log.info("Consultando mascotas de la especie ID: {}", especieId);
        return mascotaService.obtenerPorEspecieId(Long.parseLong(especieId));
    }
}