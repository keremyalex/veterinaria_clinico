package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.CarnetVacunacionInputDTO;
import com.example.microservicio_clinico.dto.CarnetVacunacionUpdateDTO;
import com.example.microservicio_clinico.entity.CarnetVacunacion;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.service.CarnetVacunacionService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class CarnetVacunacionMutationResolver {
    
    private final CarnetVacunacionService carnetVacunacionService;
    
    @DgsMutation
    public CarnetVacunacion crearCarnetVacunacion(@InputArgument CarnetVacunacionInputDTO input) {
        CarnetVacunacion carnet = new CarnetVacunacion();
        carnet.setFechaCreacion(input.getFechaCreacion());
        carnet.setObservaciones(input.getObservaciones());
        
        // Establecer mascota
        Mascota mascota = new Mascota();
        mascota.setId(input.getMascotaId());
        carnet.setMascota(mascota);
        
        return carnetVacunacionService.create(carnet);
    }
    
    @DgsMutation
    public CarnetVacunacion actualizarCarnetVacunacion(@InputArgument Long id, @InputArgument CarnetVacunacionUpdateDTO input) {
        CarnetVacunacion carnet = new CarnetVacunacion();
        carnet.setFechaCreacion(input.getFechaCreacion());
        carnet.setObservaciones(input.getObservaciones());
        
        return carnetVacunacionService.update(id, carnet);
    }
    
    @DgsMutation
    public Boolean eliminarCarnetVacunacion(@InputArgument Long id) {
        try {
            carnetVacunacionService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}