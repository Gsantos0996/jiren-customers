package com.jiren.legalsections.infraestructure.dao.mapper;

import org.mapstruct.Mapper;

import com.jiren.legalsections.domain.model.LegalSections;
import com.jiren.legalsections.infraestructure.dao.jpa.JPALegalSections;

@Mapper(componentModel = "spring")
public interface LegalSectionsMapper {

	JPALegalSections toJPALegalSections(LegalSections legalSections);
	
	LegalSections toLegalSections(JPALegalSections legalSections);
}
