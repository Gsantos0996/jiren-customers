package com.jiren.customers.infraestructure.dao.mapper;

import org.mapstruct.Mapper;

import com.jiren.customers.domain.model.Ubigeo;
import com.jiren.customers.infraestructure.dao.jpa.JPAUbigeo;

@Mapper(componentModel = "spring")
public interface UbigeoMapper {
	Ubigeo toUbigeo(JPAUbigeo jpaUbigeo);
	JPAUbigeo toJPAUbigeo(Ubigeo ubigeo);
}
