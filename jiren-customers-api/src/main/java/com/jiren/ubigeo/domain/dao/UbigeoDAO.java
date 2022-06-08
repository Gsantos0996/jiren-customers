package com.jiren.ubigeo.domain.dao;

import java.util.List;

import com.jiren.ubigeo.domain.filter.UbigeoFilter;
import com.jiren.ubigeo.service.dto.GetUbigeoLineServiceDTO;
import com.jiren.ubigeo.service.dto.GetUbigeoServiceDTO;

public interface UbigeoDAO {
	GetUbigeoLineServiceDTO getUbigeo(String guid);
	List<GetUbigeoServiceDTO> getDepartments();
	List<GetUbigeoServiceDTO> getProvinces(UbigeoFilter filter);
	List<GetUbigeoServiceDTO> getDistricts(UbigeoFilter filter);
}
