package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.service.ClienteService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class ClienteQueryResolver {
    
    private final ClienteService clienteService;
    
    @DgsQuery
    public List<Cliente> clientes() {
        return clienteService.findAll();
    }
    
    @DgsQuery
    public Cliente cliente(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return clienteService.findById(longId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }
    
    @DgsQuery
    public Cliente clienteByEmail(@InputArgument String email) {
        return clienteService.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con email: " + email));
    }
    
    @DgsQuery
    public List<Cliente> searchClientes(@InputArgument String searchTerm) {
        return clienteService.searchByName(searchTerm);
    }
}