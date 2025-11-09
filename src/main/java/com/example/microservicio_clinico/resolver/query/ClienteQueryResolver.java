package com.example.microservicio_clinico.resolver.query;

import com.example.microservicio_clinico.entity.Cliente;
import com.example.microservicio_clinico.service.ClienteService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@DgsComponent
@RequiredArgsConstructor
@Slf4j
public class ClienteQueryResolver {

    private final ClienteService clienteService;

    @DgsQuery
    public List<Cliente> clientes() {
        log.info("Consultando todos los clientes");
        return clienteService.obtenerTodos();
    }

    @DgsQuery
    public Cliente cliente(@InputArgument String id) {
        log.info("Consultando cliente con ID: {}", id);
        Optional<Cliente> cliente = clienteService.obtenerPorId(Long.parseLong(id));
        return cliente.orElse(null);
    }

    @DgsQuery
    public Cliente clientePorCi(@InputArgument String ci) {
        log.info("Consultando cliente con CI: {}", ci);
        Optional<Cliente> cliente = clienteService.obtenerPorCi(ci);
        return cliente.orElse(null);
    }
}