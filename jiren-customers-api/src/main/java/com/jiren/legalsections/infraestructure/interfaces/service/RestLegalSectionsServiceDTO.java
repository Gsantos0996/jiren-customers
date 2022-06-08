package com.jiren.legalsections.infraestructure.interfaces.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiren.legalsections.domain.dao.LegalSectionsDAO;
import com.jiren.legalsections.domain.model.type.TypeEnumerator;
import com.jiren.legalsections.service.LegalSectionsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestLegalSectionsServiceDTO implements LegalSectionsService{

	private final LegalSectionsDAO legalSectionsDAO;
	
	@Override
	@Transactional()
	public void createLegalSections(String idUser, TypeEnumerator type) {
		legalSectionsDAO.create( idUser, type );		
	}
	
	@Override
	public Boolean verifyLegalSections(String idUser, TypeEnumerator type) {
		return legalSectionsDAO.verify( idUser, type );		
	}	
}
