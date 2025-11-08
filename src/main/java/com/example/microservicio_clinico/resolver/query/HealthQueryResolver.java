package com.example.microservicio_clinico.resolver.query;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class HealthQueryResolver {

    @DgsQuery
    public String health() {
        return "OK";
    }
}