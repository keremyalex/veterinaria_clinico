package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.CitaInputDTO;
import com.example.microservicio_clinico.dto.CitaUpdateDTO;
import com.example.microservicio_clinico.entity.Cita;
import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.entity.Doctor;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.service.CitaService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class CitaMutationResolver {
    
    private final CitaService citaService;
    
    @DgsMutation
    public Cita crearCita(@InputArgument CitaInputDTO input) {
        Cita cita = new Cita();
        cita.setFechaHora(input.getFechaHora());
        cita.setMotivo(input.getMotivo());
        cita.setEstado(input.getEstado());
        
        // Establecer cliente
        Cliente cliente = new Cliente();
        cliente.setId(input.getClienteId());
        cita.setCliente(cliente);
        
        // Establecer mascota
        Mascota mascota = new Mascota();
        mascota.setId(input.getMascotaId());
        cita.setMascota(mascota);
        
        // Establecer doctor
        Doctor doctor = new Doctor();
        doctor.setId(input.getDoctorId());
        cita.setDoctor(doctor);
        
        return citaService.create(cita);
    }
    
    @DgsMutation
    public Cita actualizarCita(@InputArgument Long id, @InputArgument CitaUpdateDTO input) {
        Cita cita = new Cita();
        cita.setFechaHora(input.getFechaHora());
        cita.setMotivo(input.getMotivo());
        cita.setEstado(input.getEstado());
        
        // Establecer cliente si se proporciona
        if (input.getClienteId() != null) {
            Cliente cliente = new Cliente();
            cliente.setId(input.getClienteId());
            cita.setCliente(cliente);
        }
        
        // Establecer mascota si se proporciona
        if (input.getMascotaId() != null) {
            Mascota mascota = new Mascota();
            mascota.setId(input.getMascotaId());
            cita.setMascota(mascota);
        }
        
        // Establecer doctor si se proporciona
        if (input.getDoctorId() != null) {
            Doctor doctor = new Doctor();
            doctor.setId(input.getDoctorId());
            cita.setDoctor(doctor);
        }
        
        return citaService.update(id, cita);
    }
    
    @DgsMutation
    public Boolean eliminarCita(@InputArgument Long id) {
        try {
            citaService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}