package com.example.microservicio_clinico.resolver.query;

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
    public Cliente cliente(@InputArgument Long id) {
        return clienteService.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }
    
    @DgsQuery
    public Cliente clientePorEmail(@InputArgument String email) {
        return clienteService.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con email: " + email));
    }
    
    @DgsQuery
    public Cliente clientePorCi(@InputArgument String ci) {
        return clienteService.findByCi(ci)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado con CI: " + ci));
    }
    
    @DgsQuery
    public List<Cliente> buscarClientesPorNombre(@InputArgument String nombre) {
        return clienteService.buscarPorNombre(nombre);
    }
    
    @DgsQuery
    public List<Cliente> buscarClientesPorTelefono(@InputArgument String telefono) {
        return clienteService.buscarPorTelefono(telefono);
    }
}