package com.example.sales.controller;

import com.example.sales.Mapper.ClientTypeMapper;
import com.example.sales.dto.ClientTypeDTO;
import com.example.sales.model.ClientType;
import com.example.sales.service.ClientTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/client-types")
public class ClientTypeController {

    private final ClientTypeService clientTypeService;

    public ClientTypeController(ClientTypeService clientTypeService) {
        this.clientTypeService = clientTypeService;
    }

    @PostMapping
    public ResponseEntity<ClientTypeDTO> createClientType(@RequestBody ClientTypeDTO dto) {
        ClientType clientType = ClientTypeMapper.toEntity(dto);
        ClientType saved = clientTypeService.createClientType(clientType);
        return ResponseEntity.ok(ClientTypeMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<ClientTypeDTO>> getAllClientTypes() {
        List<ClientTypeDTO> dtos = clientTypeService.getAllClientTypes()
                .stream()
                .map(ClientTypeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientTypeDTO> getClientTypeById(@PathVariable Long id) {
        return clientTypeService.getClientTypeById(id)
                .map(ClientTypeMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientTypeDTO> updateClientType(@PathVariable Long id, @RequestBody ClientTypeDTO dto) {
        try {
            ClientType updated = clientTypeService.updateClientType(id, ClientTypeMapper.toEntity(dto));
            return ResponseEntity.ok(ClientTypeMapper.toDTO(updated));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientType(@PathVariable Long id) {
        clientTypeService.deleteClientType(id);
        return ResponseEntity.noContent().build();
    }
}
