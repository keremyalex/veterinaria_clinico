package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.BloqueHorario;
import com.example.microservicio_clinico.service.BloqueHorarioService;
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
public class BloqueHorarioQueryResolver {

    private final BloqueHorarioService bloqueHorarioService;

    @DgsQuery
    public List<BloqueHorario> bloquesHorarios() {
        log.info("Consultando todos los bloques horarios");
        return bloqueHorarioService.obtenerTodos();
    }

    @DgsQuery
    public BloqueHorario bloqueHorario(@InputArgument String id) {
        log.info("Consultando bloque horario con ID: {}", id);
        Optional<BloqueHorario> bloqueHorario = bloqueHorarioService.obtenerPorId(Long.parseLong(id));
        return bloqueHorario.orElse(null);
    }

    @DgsQuery
    public List<BloqueHorario> bloquesHorariosPorDia(@InputArgument Integer diasemana) {
        log.info("Consultando bloques horarios para el día: {}", diasemana);
        return bloqueHorarioService.obtenerPorDiaSemana(diasemana);
    }

    @DgsQuery
    public List<BloqueHorario> bloquesHorariosActivos() {
        log.info("Consultando bloques horarios activos");
        return bloqueHorarioService.obtenerActivos();
    }
}