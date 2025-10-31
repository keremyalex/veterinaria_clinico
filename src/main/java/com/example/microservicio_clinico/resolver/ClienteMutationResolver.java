package com.example.microservicio_clinico.resolver;

import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.service.ClienteService;
import com.example.microservicio_clinico.dto.ClienteInput;
import com.example.microservicio_clinico.dto.ClienteUpdateInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class ClienteMutationResolver {
    
    private final ClienteService clienteService;
    
    @DgsMutation
    public Cliente createCliente(@InputArgument ClienteInput input) {
        return clienteService.create(input);
    }
    
    @DgsMutation
    public Cliente updateCliente(@InputArgument ClienteUpdateInput input) {
        return clienteService.update(input);
    }
    
    @DgsMutation
    public Boolean deleteCliente(@InputArgument String id) {
        Long longId = Long.parseLong(id);
        return clienteService.delete(longId);
    }
}