package com.jiren.ubigeo.service;

import java.util.List;

import com.jiren.ubigeo.domain.filter.UbigeoFilter;
import com.jiren.ubigeo.service.dto.GetUbigeoLineServiceDTO;
import com.jiren.ubigeo.service.dto.GetUbigeoServiceDTO;

public interface UbigeoService {

	GetUbigeoLineServiceDTO getUbigeo(String guid);
	List<GetUbigeoServiceDTO> getDepartments();
	List<GetUbigeoServiceDTO> getProvince(UbigeoFilter filter);
	List<GetUbigeoServiceDTO> getDistrict(UbigeoFilter filter);

}
