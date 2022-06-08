package com.jiren.ubigeo.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jiren.customers.adapter.rest.dto.ubigeo.GetZoneResponseDTO;
import com.jiren.ubigeo.handler.mapper.ZoneGetMapper;
import com.jiren.ubigeo.service.ZoneService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZoneHandler {
	
	private final ZoneService zoneService;
	private final ZoneGetMapper zoneMapper;

	public List<GetZoneResponseDTO> get() {
		return zoneMapper.toGetZoneResponseDtos(zoneService.get());
	}
	

}
