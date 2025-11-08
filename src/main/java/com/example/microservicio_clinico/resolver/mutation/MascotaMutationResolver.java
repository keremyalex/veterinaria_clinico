package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.MascotaInputDTO;
import com.example.microservicio_clinico.dto.MascotaUpdateDTO;
import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.service.MascotaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class MascotaMutationResolver {
    
    private final MascotaService mascotaService;
    
    @DgsMutation
    public Mascota crearMascota(@InputArgument MascotaInputDTO input) {
        Mascota mascota = new Mascota();
        mascota.setNombre(input.getNombre());
        mascota.setRaza(input.getRaza());
        mascota.setColor(input.getColor());
        mascota.setFotocuf(input.getFotocuf());
        mascota.setObservaciones(input.getObservaciones());
        
        // Establecer cliente
        Cliente cliente = new Cliente();
        cliente.setId(input.getClienteId());
        mascota.setCliente(cliente);
        
        return mascotaService.create(mascota);
    }
    
    @DgsMutation
    public Mascota actualizarMascota(@InputArgument Long id, @InputArgument MascotaUpdateDTO input) {
        Mascota mascota = new Mascota();
        mascota.setNombre(input.getNombre());
        mascota.setRaza(input.getRaza());
        mascota.setColor(input.getColor());
        mascota.setFotocuf(input.getFotocuf());
        mascota.setObservaciones(input.getObservaciones());
        
        // Establecer cliente si se proporciona
        if (input.getClienteId() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(input.getClienteId());
            mascota.setCliente(cliente);
        }
        
        return mascotaService.update(id, mascota);
    }
    
    @DgsMutation
    public Boolean eliminarMascota(@InputArgument Long id) {
        try {
            mascotaService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}