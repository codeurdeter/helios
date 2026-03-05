package com.example.demo.service;

import com.example.demo.dto.ClientRequest;
import com.example.demo.dto.ClientResponse;

public interface ClientService {
    ClientResponse create(ClientRequest clientRequest);
    ClientResponse get(Long id);
    void delete(Long id);
    ClientResponse modify(Long id, ClientRequest clientRequest);
}
