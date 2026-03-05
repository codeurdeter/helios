package com.example.demo.service;

import com.example.demo.dto.ClientRequest;
import com.example.demo.dto.ClientResponse;
import com.example.demo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientResponse create(ClientRequest clientRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void modify(Long id, ClientRequest clientRequest) {

    }
}
