package com.example.sales.Mapper;

import com.example.sales.dto.ClientDTO;
import com.example.sales.model.Client;
public class ClientMapper {

    public static Client toEntity(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        // clientType et route seront affectés dans le service
        return client;
    }

    public static ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setClientTypeName(client.getClientType().getName()); // si getClientType() != null
        dto.setRouteId(client.getRoute().getId());
        return dto;
    }
}


