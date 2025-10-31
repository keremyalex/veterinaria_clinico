package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.repository.ClienteRepository;
import com.example.microservicio_clinico.dto.ClienteInput;
import com.example.microservicio_clinico.dto.ClienteUpdateInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    public List<Cliente> searchByName(String searchTerm) {
        return clienteRepository.findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCase(
                searchTerm, searchTerm);
    }
    
    public Cliente create(ClienteInput input) {
        if (clienteRepository.existsByEmail(input.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con el email: " + input.getEmail());
        }
        
        LocalDate fechaNacimiento = LocalDate.parse(input.getFechaNacimiento(), dateFormatter);
        
        Cliente cliente = new Cliente(
                input.getNombre(),
                input.getApellidos(),
                input.getEmail(),
                input.getTelefono(),
                fechaNacimiento
        );
        
        return clienteRepository.save(cliente);
    }
    
    public Cliente update(ClienteUpdateInput input) {
        Long longId = Long.parseLong(input.getId());
        Cliente cliente = clienteRepository.findById(longId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + input.getId()));
        
        // Verificar si el nuevo email ya existe en otro cliente
        Optional<Cliente> existingCliente = clienteRepository.findByEmail(input.getEmail());
        if (existingCliente.isPresent() && !existingCliente.get().getId().equals(longId)) {
            throw new RuntimeException("Ya existe otro cliente con el email: " + input.getEmail());
        }
        
        LocalDate fechaNacimiento = LocalDate.parse(input.getFechaNacimiento(), dateFormatter);
        
        cliente.setNombre(input.getNombre());
        cliente.setApellidos(input.getApellidos());
        cliente.setEmail(input.getEmail());
        cliente.setTelefono(input.getTelefono());
        cliente.setFechaNacimiento(fechaNacimiento);
        
        return clienteRepository.save(cliente);
    }
    
    public boolean delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
        
        clienteRepository.deleteById(id);
        return true;
    }
}