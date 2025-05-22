package com.example.sales.controller;

import com.example.sales.Mapper.ClientMapper;
import com.example.sales.dto.ClientDTO;
import com.example.sales.model.Client;
import com.example.sales.repository.ClientRepository;
import com.example.sales.repository.RouteRepository;
import com.example.sales.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController

@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;
    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody ClientDTO dto) {
        try {
            Client client = ClientMapper.toEntity(dto);
            Client savedClient = clientService.createClient(client, dto.getClientTypeName(), dto.getRouteId());
            ClientDTO responseDto = ClientMapper.toDTO(savedClient);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création du client.");
        }
    }



    @PutMapping("/{clientId}/assign-type/{clientTypeId}")
    public ResponseEntity<Client> assignClientType(
            @PathVariable Long clientId,
            @PathVariable Long clientTypeId) {

        Client updatedClient = clientService.assignClientType(clientId, clientTypeId);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientDTO> dtos = clients.stream()
                .map(ClientMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ClientMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO dto) {
        try {
            Client updatedEntity = clientService.updateClient(id, ClientMapper.toEntity(dto));
            return ResponseEntity.ok(ClientMapper.toDTO(updatedEntity));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
