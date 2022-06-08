package com.jiren.legalsections.infraestructure.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jiren.legalsections.infraestructure.dao.jpa.JPALegalSections;

public interface LegalSectionsRepository extends JpaRepository<JPALegalSections, String> {

	Optional<JPALegalSections> findByUserGuidAndType(String guid, Integer type);
}
