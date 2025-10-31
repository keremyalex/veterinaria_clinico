package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Mascota;
import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.entity.Especie;
import com.example.microservicio_clinico.repository.MascotaRepository;
import com.example.microservicio_clinico.repository.ClienteRepository;
import com.example.microservicio_clinico.repository.EspecieRepository;
import com.example.microservicio_clinico.dto.MascotaInput;
import com.example.microservicio_clinico.dto.MascotaUpdateInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MascotaService {
    
    private final MascotaRepository mascotaRepository;
    private final ClienteRepository clienteRepository;
    private final EspecieRepository especieRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public List<Mascota> findAll() {
        return mascotaRepository.findAll();
    }
    
    public Optional<Mascota> findById(Long id) {
        return mascotaRepository.findById(id);
    }
    
    public List<Mascota> findByClienteId(Long clienteId) {
        return mascotaRepository.findByClienteId(clienteId);
    }
    
    public List<Mascota> findByEspecieId(Long especieId) {
        return mascotaRepository.findByEspecieId(especieId);
    }
    
    public List<Mascota> searchByNombreOrRaza(String searchTerm) {
        return mascotaRepository.searchByNombreOrRaza(searchTerm);
    }
    
    public List<Mascota> findBySexo(String sexo) {
        return mascotaRepository.findBySexo(sexo);
    }
    
    @Transactional
    public Mascota create(MascotaInput input) {
        try {
            log.info("Creando mascota con input: {}", input);
            
            // Validar entrada
            if (input == null) {
                throw new RuntimeException("MascotaInput no puede ser null");
            }
            
            // Validar campos obligatorios
            if (input.getNombre() == null || input.getNombre().trim().isEmpty()) {
                throw new RuntimeException("El nombre de la mascota es obligatorio");
            }
            
            if (input.getSexo() == null || (!input.getSexo().equals("M") && !input.getSexo().equals("F"))) {
                throw new RuntimeException("El sexo debe ser 'M' (Macho) o 'F' (Hembra)");
            }
            
            if (input.getClienteId() == null) {
                throw new RuntimeException("El ID del cliente es obligatorio");
            }
            
            if (input.getEspecieId() == null) {
                throw new RuntimeException("El ID de la especie es obligatorio");
            }
            
            // Buscar cliente
            Long clienteId = Long.parseLong(input.getClienteId());
            log.info("Buscando cliente con ID: {}", clienteId);
            Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + input.getClienteId()));
            log.info("Cliente encontrado: {}", cliente.getNombre());
            
            // Buscar especie
            Long especieId = Long.parseLong(input.getEspecieId());
            log.info("Buscando especie con ID: {}", especieId);
            Especie especie = especieRepository.findById(especieId)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada con ID: " + input.getEspecieId()));
            log.info("Especie encontrada: {}", especie.getDescripcion());
            
            LocalDate fechaNacimiento = LocalDate.parse(input.getFechaNacimiento(), dateFormatter);
            BigDecimal peso = input.getPeso() != null ? BigDecimal.valueOf(input.getPeso()) : null;
            
            Mascota mascota = new Mascota(
                    input.getNombre(),
                    input.getSexo(),
                    input.getRaza(),
                    fechaNacimiento,
                    peso,
                    cliente,
                    especie
            );
            
            Mascota savedMascota = mascotaRepository.save(mascota);
            
            if (savedMascota == null) {
                throw new RuntimeException("Error al guardar la mascota en la base de datos");
            }
            
            log.info("Mascota creada exitosamente con ID: {}", savedMascota.getId());
            log.info("Cliente asociado: {}", savedMascota.getCliente().getNombre());
            log.info("Especie asociada: {}", savedMascota.getEspecie().getDescripcion());
            
            return savedMascota;
            
        } catch (NumberFormatException e) {
            throw new RuntimeException("ID de cliente o especie inválido: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear mascota: " + e.getMessage(), e);
        }
    }
    
    public Mascota update(MascotaUpdateInput input) {
        Long longId = Long.parseLong(input.getId());
        Mascota mascota = mascotaRepository.findById(longId)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + input.getId()));
        
        // Validar sexo
        if (!input.getSexo().equals("M") && !input.getSexo().equals("F")) {
            throw new RuntimeException("El sexo debe ser 'M' (Macho) o 'F' (Hembra)");
        }
        
        // Buscar cliente si cambió
        Long clienteId = Long.parseLong(input.getClienteId());
        if (!mascota.getCliente().getId().equals(clienteId)) {
            Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + input.getClienteId()));
            mascota.setCliente(cliente);
        }
        
        // Buscar especie si cambió
        Long especieId = Long.parseLong(input.getEspecieId());
        if (!mascota.getEspecie().getId().equals(especieId)) {
            Especie especie = especieRepository.findById(especieId)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada con ID: " + input.getEspecieId()));
            mascota.setEspecie(especie);
        }
        
        LocalDate fechaNacimiento = LocalDate.parse(input.getFechaNacimiento(), dateFormatter);
        BigDecimal peso = input.getPeso() != null ? BigDecimal.valueOf(input.getPeso()) : null;
        
        mascota.setNombre(input.getNombre());
        mascota.setSexo(input.getSexo());
        mascota.setRaza(input.getRaza());
        mascota.setFechaNacimiento(fechaNacimiento);
        mascota.setPeso(peso);
        
        return mascotaRepository.save(mascota);
    }
    
    public boolean delete(Long id) {
        if (!mascotaRepository.existsById(id)) {
            throw new RuntimeException("Mascota no encontrada con ID: " + id);
        }
        
        mascotaRepository.deleteById(id);
        return true;
    }
}