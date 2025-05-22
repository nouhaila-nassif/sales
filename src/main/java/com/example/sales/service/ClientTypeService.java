package com.example.sales.service;

import com.example.sales.model.ClientType;
import com.example.sales.repository.ClientTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientTypeService {

    private final ClientTypeRepository clientTypeRepository;

    public ClientTypeService(ClientTypeRepository clientTypeRepository) {
        this.clientTypeRepository = clientTypeRepository;
    }

    public ClientType createClientType(ClientType clientType) {
        if (clientTypeRepository.existsByName(clientType.getName())) {
            throw new IllegalArgumentException("ClientType already exists");
        }
        return clientTypeRepository.save(clientType);
    }

    public List<ClientType> getAllClientTypes() {
        return clientTypeRepository.findAll();
    }

    public Optional<ClientType> getClientTypeById(Long id) {
        return clientTypeRepository.findById(id);
    }

    public ClientType updateClientType(Long id, ClientType clientType) {
        return clientTypeRepository.findById(id).map(ct -> {
            ct.setName(clientType.getName());
            return clientTypeRepository.save(ct);
        }).orElseThrow(() -> new IllegalArgumentException("ClientType not found"));
    }

    public void deleteClientType(Long id) {
        clientTypeRepository.deleteById(id);
    }
}
