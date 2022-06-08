package com.jiren.ubigeo.infraestructure.interfaces.service;

import java.util.List;

import com.jiren.ubigeo.service.dto.GetUbigeoLineServiceDTO;
import org.springframework.stereotype.Service;

import com.jiren.ubigeo.domain.dao.UbigeoDAO;
import com.jiren.ubigeo.domain.filter.UbigeoFilter;
import com.jiren.ubigeo.service.UbigeoService;
import com.jiren.ubigeo.service.dto.GetUbigeoServiceDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestUbigeoServiceDTO implements UbigeoService{

	private final UbigeoDAO ubigeoDAO;

	@Override
	public GetUbigeoLineServiceDTO getUbigeo(String guid) {
		return ubigeoDAO.getUbigeo(guid);
	}

	@Override
	public List<GetUbigeoServiceDTO> getDepartments() {
		return ubigeoDAO.getDepartments();
	}

	@Override
	public List<GetUbigeoServiceDTO> getProvince(UbigeoFilter filter) {
		return ubigeoDAO.getProvinces(filter);
	}

	@Override
	public List<GetUbigeoServiceDTO> getDistrict(UbigeoFilter filter) {
		return ubigeoDAO.getDistricts(filter);
	}

}
