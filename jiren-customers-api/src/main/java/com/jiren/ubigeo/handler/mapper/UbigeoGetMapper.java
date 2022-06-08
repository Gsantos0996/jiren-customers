package com.jiren.ubigeo.handler.mapper;

import java.util.List;

import com.jiren.customers.adapter.rest.dto.ubigeo.GetUbigeoLineResponseDTO;
import com.jiren.ubigeo.service.dto.GetUbigeoLineServiceDTO;
import org.mapstruct.Mapper;

import com.jiren.customers.adapter.rest.dto.ubigeo.GetUbigeoResponseDTO;
import com.jiren.ubigeo.service.dto.GetUbigeoServiceDTO;

@Mapper(componentModel = "spring")
public interface UbigeoGetMapper {

	List<GetUbigeoResponseDTO> toGetUbigeoResponseDtos(List<GetUbigeoServiceDTO> listGetUbigeoServiceDTO);

	GetUbigeoLineResponseDTO toGetUbigeoLineResponseDto(GetUbigeoLineServiceDTO ubigeoLineServiceDTO);
}
