package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.DetalleVacunacionInputDTO;
import com.example.microservicio_clinico.dto.DetalleVacunacionUpdateDTO;
import com.example.microservicio_clinico.entity.CarnetVacunacion;
import com.example.microservicio_clinico.entity.DetalleVacunacion;
import com.example.microservicio_clinico.entity.Vacuna;
import com.example.microservicio_clinico.service.DetalleVacunacionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class DetalleVacunacionMutationResolver {
    
    private final DetalleVacunacionService detalleVacunacionService;
    
    @DgsMutation
    public DetalleVacunacion crearDetalleVacunacion(@InputArgument DetalleVacunacionInputDTO input) {
        DetalleVacunacion detalle = new DetalleVacunacion();
        detalle.setFechaAplicacion(input.getFechaAplicacion());
        detalle.setFechaProximaDosis(input.getFechaProximaDosis());
        detalle.setNumeroDosis(input.getNumeroDosis());
        detalle.setLote(input.getLote());
        detalle.setVeterinarioAplicador(input.getVeterinarioAplicador());
        detalle.setObservaciones(input.getObservaciones());
        
        // Establecer carnet de vacunación
        CarnetVacunacion carnet = new CarnetVacunacion();
        carnet.setId(input.getCarnetVacunacionId());
        detalle.setCarnetVacunacion(carnet);
        
        // Establecer vacuna
        Vacuna vacuna = new Vacuna();
        vacuna.setId(input.getVacunaId());
        detalle.setVacuna(vacuna);
        
        return detalleVacunacionService.create(detalle);
    }
    
    @DgsMutation
    public DetalleVacunacion actualizarDetalleVacunacion(@InputArgument Long id, @InputArgument DetalleVacunacionUpdateDTO input) {
        DetalleVacunacion detalle = new DetalleVacunacion();
        detalle.setFechaAplicacion(input.getFechaAplicacion());
        detalle.setFechaProximaDosis(input.getFechaProximaDosis());
        detalle.setNumeroDosis(input.getNumeroDosis());
        detalle.setLote(input.getLote());
        detalle.setVeterinarioAplicador(input.getVeterinarioAplicador());
        detalle.setObservaciones(input.getObservaciones());
        
        // Establecer vacuna si se proporciona
        if (input.getVacunaId() != null) {
            Vacuna vacuna = new Vacuna();
            vacuna.setId(input.getVacunaId());
            detalle.setVacuna(vacuna);
        }
        
        return detalleVacunacionService.update(id, detalle);
    }
    
    @DgsMutation
    public Boolean eliminarDetalleVacunacion(@InputArgument Long id) {
        try {
            detalleVacunacionService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}