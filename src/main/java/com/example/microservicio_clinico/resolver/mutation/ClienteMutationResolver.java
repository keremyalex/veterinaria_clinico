package com.example.microservicio_clinico.resolver.mutation;

import com.example.microservicio_clinico.dto.ClienteInput;
import com.example.microservicio_clinico.dto.ClienteOutput;
import com.example.microservicio_clinico.dto.ClienteUpdateInput;
import com.example.microservicio_clinico.service.ClienteService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class ClienteMutationResolver {
    
    private final ClienteService clienteService;
    
    @DgsMutation
    public ClienteOutput crearCliente(@InputArgument ClienteInput input) {
        log.info("GraphQL Mutation: Creando cliente con CI: {}", input.getCi());
        return clienteService.crearCliente(input);
    }
    
    @DgsMutation
    public ClienteOutput actualizarCliente(@InputArgument ClienteUpdateInput input) {
        log.info("GraphQL Mutation: Actualizando cliente con ID: {}", input.getId());
        return clienteService.actualizarCliente(input);
    }
    
    @DgsMutation
    public Boolean eliminarCliente(@InputArgument Integer id) {
        log.info("GraphQL Mutation: Eliminando cliente con ID: {}", id);
        return clienteService.eliminarCliente(id);
    }
}