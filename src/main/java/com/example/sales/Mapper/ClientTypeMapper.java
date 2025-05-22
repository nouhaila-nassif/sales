package com.example.sales.Mapper;

import com.example.sales.dto.ClientTypeDTO;
import com.example.sales.model.ClientType;

public class ClientTypeMapper {

    public static ClientTypeDTO toDTO(ClientType entity) {
        return new ClientTypeDTO(entity.getId(), entity.getName());
    }

    public static ClientType toEntity(ClientTypeDTO dto) {
        ClientType ct = new ClientType();
        ct.setName(dto.getName());
        return ct;
    }
}
