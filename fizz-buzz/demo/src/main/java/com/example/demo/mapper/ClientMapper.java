package com.example.demo.mapper;

import com.example.demo.dto.ClientRequest;
import com.example.demo.dto.ClientResponse;
import com.example.demo.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client toEntity(ClientRequest clientRequest);
    ClientResponse toResponse(Client client);
    void modifyEntity(ClientRequest clientRequest, @MappingTarget Client client);
}
