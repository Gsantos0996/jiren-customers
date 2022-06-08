package com.jiren.ubigeo.handler;

import java.util.List;

import com.jiren.customers.adapter.rest.dto.ubigeo.GetUbigeoLineResponseDTO;
import org.springframework.stereotype.Component;

import com.jiren.customers.adapter.rest.dto.ubigeo.GetUbigeoResponseDTO;
import com.jiren.ubigeo.domain.filter.UbigeoFilter;
import com.jiren.ubigeo.handler.mapper.UbigeoGetMapper;
import com.jiren.ubigeo.service.UbigeoService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UbigeoHandler {
	
	private final UbigeoService ubigeoService;
	private final UbigeoGetMapper ubigeoMapper;

	public GetUbigeoLineResponseDTO getUbigeo(String guid){
		return ubigeoMapper.toGetUbigeoLineResponseDto(ubigeoService.getUbigeo(guid));
	}

	public List<GetUbigeoResponseDTO> getDepartments() {
		return ubigeoMapper.toGetUbigeoResponseDtos(ubigeoService.getDepartments());
	}
	
	public List<GetUbigeoResponseDTO> getProvinces(UbigeoFilter filter) {
		return ubigeoMapper.toGetUbigeoResponseDtos(ubigeoService.getProvince(filter));
	}

	public List<GetUbigeoResponseDTO> getDistrict(UbigeoFilter filter) {
		return ubigeoMapper.toGetUbigeoResponseDtos(ubigeoService.getDistrict(filter));
	}

}
