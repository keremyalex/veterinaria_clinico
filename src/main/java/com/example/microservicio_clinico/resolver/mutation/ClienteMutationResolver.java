package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.ClienteInputDTO;
import com.example.microservicio_clinico.dto.ClienteUpdateDTO;
import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.service.ClienteService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class ClienteMutationResolver {
    
    private final ClienteService clienteService;
    
    @DgsMutation
    public Cliente crearCliente(@InputArgument ClienteInputDTO input) {
        Cliente cliente = new Cliente();
        cliente.setNombre(input.getNombre());
        cliente.setApellidos(input.getApellidos());
        cliente.setCi(input.getCi());
        cliente.setTelefono(input.getTelefono());
        cliente.setEmail(input.getEmail());
        cliente.setFotocuf(input.getFotocuf());
        
        return clienteService.create(cliente);
    }
    
    @DgsMutation
    public Cliente actualizarCliente(@InputArgument Long id, @InputArgument ClienteUpdateDTO input) {
        Cliente cliente = new Cliente();
        cliente.setNombre(input.getNombre());
        cliente.setApellidos(input.getApellidos());
        cliente.setCi(input.getCi());
        cliente.setTelefono(input.getTelefono());
        cliente.setEmail(input.getEmail());
        cliente.setFotocuf(input.getFotocuf());
        
        return clienteService.update(id, cliente);
    }
    
    @DgsMutation
    public Boolean eliminarCliente(@InputArgument Long id) {
        try {
            clienteService.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}