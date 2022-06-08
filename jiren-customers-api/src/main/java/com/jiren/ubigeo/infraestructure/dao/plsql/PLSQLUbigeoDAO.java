package com.jiren.ubigeo.infraestructure.dao.plsql;

import com.jiren.shared.exception.MPlusApiException;
import com.jiren.ubigeo.domain.dao.UbigeoDAO;
import com.jiren.ubigeo.domain.exeption.enumerator.UbigeoExceptionEnumerator;
import com.jiren.ubigeo.domain.filter.UbigeoFilter;
import com.jiren.ubigeo.infraestructure.dao.jpa.JpaUbigeo;
import com.jiren.ubigeo.infraestructure.dao.repository.UbigeosRepository;
import com.jiren.ubigeo.service.dto.GetUbigeoLineServiceDTO;
import com.jiren.ubigeo.service.dto.GetUbigeoServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PLSQLUbigeoDAO implements UbigeoDAO {
	
	private final UbigeosRepository ubigeoRepository;

	@Override
	public GetUbigeoLineServiceDTO getUbigeo(String guid) {
		JpaUbigeo jpaUbigeo = ubigeoRepository.findById(guid)
				.orElseThrow(()->new MPlusApiException(UbigeoExceptionEnumerator.NOT_FOUND_UBIGEO));

		return buildGetUbigeoLineServiceDTO(jpaUbigeo);
	}

	@Override
	public List<GetUbigeoServiceDTO> getDepartments() {
		return ubigeoRepository.findAllDepartments().stream()
				.map(x -> new GetUbigeoServiceDTO(x.getCode(), x.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public List<GetUbigeoServiceDTO> getProvinces(UbigeoFilter filter) {
		return ubigeoRepository.findProvincesByDepartment(filter.getDepartment()).stream()
				.map(x -> new GetUbigeoServiceDTO(x.getCode(), x.getName()))
				.collect(Collectors.toList());
	}

	@Override
	public List<GetUbigeoServiceDTO> getDistricts(UbigeoFilter filter) {
		return ubigeoRepository.findDistricsByProvince(filter.getDepartment(), filter.getProvince()).stream()
				.map(x -> new GetUbigeoServiceDTO(x.getCode(), x.getName()))
				.collect(Collectors.toList());
	}

	private GetUbigeoLineServiceDTO buildGetUbigeoLineServiceDTO(JpaUbigeo jpaUbigeo){
		return GetUbigeoLineServiceDTO.builder()
				.id(jpaUbigeo.getGuid())
				.district(jpaUbigeo.getDistrict())
				.province(jpaUbigeo.getProvince())
				.department(jpaUbigeo.getDepartment())
				.build();

	}

}
