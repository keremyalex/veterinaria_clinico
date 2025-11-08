package com.example.microservicio_clinico.service;

import com.example.microservicio_clinico.dto.*;
import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    // Crear nuevo cliente
    public ClienteOutput crearCliente(ClienteInput input) {
        log.info("Creando nuevo cliente con CI: {}", input.getCi());
        
        // Validar que el CI no exista
        if (clienteRepository.findByCi(input.getCi()).isPresent()) {
            throw new RuntimeException("Ya existe un cliente con el CI: " + input.getCi());
        }
        
        // Validar que el teléfono no exista
        if (clienteRepository.findByTelefono(input.getTelefono()).isPresent()) {
            throw new RuntimeException("Ya existe un cliente con el teléfono: " + input.getTelefono());
        }
        
        Cliente cliente = new Cliente();
        cliente.setNombre(input.getNombre());
        cliente.setApellido(input.getApellido());
        cliente.setCi(input.getCi());
        cliente.setTelefono(input.getTelefono());
        cliente.setFotourl(input.getFotourl());
        
        Cliente savedCliente = clienteRepository.save(cliente);
        log.info("Cliente creado exitosamente con ID: {}", savedCliente.getId());
        
        return convertirAOutput(savedCliente);
    }
    
    // Actualizar cliente
    public ClienteOutput actualizarCliente(ClienteUpdateInput input) {
        log.info("Actualizando cliente con ID: {}", input.getId());
        
        Cliente cliente = clienteRepository.findById(input.getId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + input.getId()));
        
        // Validar CI único si se está cambiando
        if (input.getCi() != null && !input.getCi().equals(cliente.getCi())) {
            if (clienteRepository.existsByCiAndIdNot(input.getCi(), input.getId())) {
                throw new RuntimeException("Ya existe otro cliente con el CI: " + input.getCi());
            }
            cliente.setCi(input.getCi());
        }
        
        // Validar teléfono único si se está cambiando
        if (input.getTelefono() != null && !input.getTelefono().equals(cliente.getTelefono())) {
            if (clienteRepository.existsByTelefonoAndIdNot(input.getTelefono(), input.getId())) {
                throw new RuntimeException("Ya existe otro cliente con el teléfono: " + input.getTelefono());
            }
            cliente.setTelefono(input.getTelefono());
        }
        
        // Actualizar otros campos si se proporcionan
        if (input.getNombre() != null) cliente.setNombre(input.getNombre());
        if (input.getApellido() != null) cliente.setApellido(input.getApellido());
        if (input.getFotourl() != null) cliente.setFotourl(input.getFotourl());
        
        Cliente updatedCliente = clienteRepository.save(cliente);
        log.info("Cliente actualizado exitosamente con ID: {}", updatedCliente.getId());
        
        return convertirAOutput(updatedCliente);
    }
    
    // Obtener cliente por ID
    @Transactional(readOnly = true)
    public ClienteOutput obtenerClientePorId(Integer id) {
        log.info("Obteniendo cliente por ID: {}", id);
        
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        
        return convertirAOutput(cliente);
    }
    
    // Obtener todos los clientes
    @Transactional(readOnly = true)
    public List<ClienteOutput> obtenerTodosLosClientes() {
        log.info("Obteniendo todos los clientes");
        
        return clienteRepository.findAll()
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Buscar clientes por nombre o apellido
    @Transactional(readOnly = true)
    public List<ClienteOutput> buscarClientesPorNombre(String termino) {
        log.info("Buscando clientes por término: {}", termino);
        
        return clienteRepository.findByNombreOrApellidoContainingIgnoreCase(termino, termino)
            .stream()
            .map(this::convertirAOutput)
            .collect(Collectors.toList());
    }
    
    // Obtener cliente por CI
    @Transactional(readOnly = true)
    public Optional<ClienteOutput> obtenerClientePorCi(String ci) {
        log.info("Obteniendo cliente por CI: {}", ci);
        
        return clienteRepository.findByCi(ci)
            .map(this::convertirAOutput);
    }
    
    // Obtener cliente por teléfono
    @Transactional(readOnly = true)
    public Optional<ClienteOutput> obtenerClientePorTelefono(String telefono) {
        log.info("Obteniendo cliente por teléfono: {}", telefono);
        
        return clienteRepository.findByTelefono(telefono)
            .map(this::convertirAOutput);
    }
    
    // Eliminar cliente
    public boolean eliminarCliente(Integer id) {
        log.info("Eliminando cliente con ID: {}", id);
        
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
        
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado exitosamente con ID: {}", id);
        
        return true;
    }
    
    // Método privado para convertir Entity a DTO
    private ClienteOutput convertirAOutput(Cliente cliente) {
        ClienteOutput output = new ClienteOutput();
        output.setId(cliente.getId());
        output.setNombre(cliente.getNombre());
        output.setApellido(cliente.getApellido());
        output.setCi(cliente.getCi());
        output.setTelefono(cliente.getTelefono());
        output.setFotourl(cliente.getFotourl());
        // Las mascotas se cargarán por separado cuando sea necesario
        return output;
    }
}
