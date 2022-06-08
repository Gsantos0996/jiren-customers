package com.jiren.ubigeo.handler.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.jiren.customers.adapter.rest.dto.ubigeo.GetZoneResponseDTO;
import com.jiren.ubigeo.service.dto.GetZoneServiceDTO;

@Mapper(componentModel = "spring")
public interface ZoneGetMapper {

	List<GetZoneResponseDTO> toGetZoneResponseDtos(List<GetZoneServiceDTO> listGetZoneServiceDTO);
}
