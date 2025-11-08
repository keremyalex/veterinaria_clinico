package com.example.microservicio_clinico.resolver.federation;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.service.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsEntityFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class EntityResolver {
    
    private final ClienteService clienteService;
    private final DoctorService doctorService;
    private final MascotaService mascotaService;
    private final EspecieService especieService;

    @DgsEntityFetcher(name = "Cliente")
    public ClienteOutput cliente(Map<String, Object> values) {
        log.info("Resolviendo entidad Cliente con ID: {}", values.get("id"));
        Integer id = Integer.valueOf(values.get("id").toString());
        return clienteService.obtenerClientePorId(id);
    }

    @DgsEntityFetcher(name = "Doctor")
    public DoctorOutput doctor(Map<String, Object> values) {
        log.info("Resolviendo entidad Doctor con ID: {}", values.get("id"));
        Integer id = Integer.valueOf(values.get("id").toString());
        return doctorService.obtenerDoctorPorId(id);
    }

    @DgsEntityFetcher(name = "Mascota")
    public MascotaOutput mascota(Map<String, Object> values) {
        log.info("Resolviendo entidad Mascota con ID: {}", values.get("id"));
        Integer id = Integer.valueOf(values.get("id").toString());
        return mascotaService.obtenerMascotaPorId(id);
    }

    @DgsEntityFetcher(name = "Especie")
    public EspecieOutput especie(Map<String, Object> values) {
        log.info("Resolviendo entidad Especie con ID: {}", values.get("id"));
        Integer id = Integer.valueOf(values.get("id").toString());
        return especieService.obtenerEspeciePorId(id);
    }
}