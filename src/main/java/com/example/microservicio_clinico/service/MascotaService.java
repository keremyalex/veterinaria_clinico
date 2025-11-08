package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.entity.Especie;
import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.repository.ClienteRepository;
import com.example.microservicio_clinico.repository.EspecieRepository;
import com.example.microservicio_clinico.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MascotaService {
    
    private final MascotaRepository mascotaRepository;
    private final ClienteRepository clienteRepository;
    private final EspecieRepository especieRepository;
    private final ClienteService clienteService;
    private final EspecieService especieService;
    
    // Crear nueva mascota
    public MascotaOutput crearMascota(MascotaInput input) {
        log.info("Creando nueva mascota: {} para cliente ID: {}", input.getNombre(), input.getClienteId());
        
        // Validar que el cliente exista
        Cliente cliente = clienteRepository.findById(input.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + input.getClienteId()));
        
        // Validar que la especie exista
        Especie especie = especieRepository.findById(input.getEspecieId())
            .orElseThrow(() -> new RuntimeException("Especie no encontrada con ID: " + input.getEspecieId()));
        
        Mascota mascota = new Mascota();
        mascota.setNombre(input.getNombre());
        mascota.setSexo(input.getSexo());
        mascota.setRaza(input.getRaza());
        mascota.setFotourl(input.getFotoUrl());
        mascota.setFechanacimiento(input.getFechanacimiento());
        mascota.setCliente(cliente);
        mascota.setEspecie(especie);
        
        Mascota savedMascota = mascotaRepository.save(mascota);
        log.info("Mascota creada exitosamente con ID: {}", savedMascota.getId());
        
        return convertirAOutput(savedMascota);
    }
    
    // Actualizar mascota
    public MascotaOutput actualizarMascota(MascotaUpdateInput input) {
        log.info("Actualizando mascota con ID: {}", input.getId());
        
        Mascota mascota = mascotaRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + input.getId()));
        
        // Actualizar cliente si se proporciona
        if (input.getClienteId() != null && !input.getClienteId().equals(mascota.getCliente().getId())) {
            Cliente cliente = clienteRepository.findById(input.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + input.getClienteId()));
            mascota.setCliente(cliente);
        }
        
        // Actualizar especie si se proporciona
        if (input.getEspecieId() != null && !input.getEspecieId().equals(mascota.getEspecie().getId())) {
            Especie especie = especieRepository.findById(input.getEspecieId())
                .orElseThrow(() -> new RuntimeException("Especie no encontrada con ID: " + input.getEspecieId()));
            mascota.setEspecie(especie);
        }
        
        // Actualizar otros campos si se proporcionan
        if (input.getNombre() != null) mascota.setNombre(input.getNombre());
        if (input.getSexo() != null) mascota.setSexo(input.getSexo());
        if (input.getRaza() != null) mascota.setRaza(input.getRaza());
        if (input.getFotoUrl() != null) mascota.setFotourl(input.getFotoUrl());
        if (input.getFechanacimiento() != null) mascota.setFechanacimiento(input.getFechanacimiento());
        
        Mascota updatedMascota = mascotaRepository.save(mascota);
        log.info("Mascota actualizada exitosamente con ID: {}", updatedMascota.getId());
        
        return convertirAOutput(updatedMascota);
    }
    
    // Obtener mascota por ID
    @Transactional(readOnly = true)
    public MascotaOutput obtenerMascotaPorId(Integer id) {
        log.info("Obteniendo mascota por ID: {}", id);
        
        Mascota mascota = mascotaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
        
        return convertirAOutput(mascota);
    }
    
    // Obtener todas las mascotas
    @Transactional(readOnly = true)
    public List<MascotaOutput> obtenerTodasLasMascotas() {
        log.info("Obteniendo todas las mascotas");
        
        return mascotaRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener mascotas por cliente
    @Transactional(readOnly = true)
    public List<MascotaOutput> obtenerMascotasPorCliente(Integer clienteId) {
        log.info("Obteniendo mascotas del cliente ID: {}", clienteId);
        
        return mascotaRepository.findByClienteId(clienteId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener mascotas por especie
    @Transactional(readOnly = true)
    public List<MascotaOutput> obtenerMascotasPorEspecie(Integer especieId) {
        log.info("Obteniendo mascotas de la especie ID: {}", especieId);
        
        return mascotaRepository.findByEspecieId(especieId)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar mascotas por nombre
    @Transactional(readOnly = true)
    public List<MascotaOutput> buscarMascotasPorNombre(String nombre) {
        log.info("Buscando mascotas por nombre: {}", nombre);
        
        return mascotaRepository.findByNombreContainingIgnoreCase(nombre)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar mascotas por raza
    @Transactional(readOnly = true)
    public List<MascotaOutput> buscarMascotasPorRaza(String raza) {
        log.info("Buscando mascotas por raza: {}", raza);
        
        return mascotaRepository.findByRazaContainingIgnoreCase(raza)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener mascotas por sexo
    @Transactional(readOnly = true)
    public List<MascotaOutput> obtenerMascotasPorSexo(Character sexo) {
        log.info("Obteniendo mascotas por sexo: {}", sexo);
        
        return mascotaRepository.findBySexo(sexo)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener mascotas por rango de fechas de nacimiento
    @Transactional(readOnly = true)
    public List<MascotaOutput> obtenerMascotasPorRangoFechaNacimiento(LocalDate fechaInicio, LocalDate fechaFin) {
        log.info("Obteniendo mascotas por rango de fechas: {} - {}", fechaInicio, fechaFin);
        
        return mascotaRepository.findByFechanacimientoBetween(fechaInicio, fechaFin)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Eliminar mascota
    public boolean eliminarMascota(Integer id) {
        log.info("Eliminando mascota con ID: {}", id);
        
        if (!mascotaRepository.existsById(id)) {
            throw new RuntimeException("Mascota no encontrada con ID: " + id);
        }
        
        mascotaRepository.deleteById(id);
        log.info("Mascota eliminada exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private MascotaOutput convertirAOutput(Mascota mascota) {
        MascotaOutput output = new MascotaOutput();
        output.setId(mascota.getId());
        output.setNombre(mascota.getNombre());
        output.setSexo(mascota.getSexo());
        output.setRaza(mascota.getRaza());
        output.setFotoUrl(mascota.getFotourl());
        output.setFechanacimiento(mascota.getFechanacimiento());
        
        // Convertir cliente y especie
        output.setCliente(clienteService.obtenerClientePorId(mascota.getCliente().getId()));
        output.setEspecie(especieService.obtenerEspeciePorId(mascota.getEspecie().getId()));
        
        // El carnet de vacunación se cargará por separado cuando sea necesario
        
        return output;
    }
}
