package com.jiren.legalsections.handler;

import org.springframework.stereotype.Component;

import com.jiren.legalsections.domain.model.type.TypeEnumerator;
import com.jiren.legalsections.service.LegalSectionsService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LegalSectionsHandler {
	
	private final LegalSectionsService legalSectionsService;
	
	public void saveLegalSections(String idUser, TypeEnumerator type) {
		legalSectionsService.createLegalSections(idUser, type);
	}

	public Boolean verifyLegalSections(String idUser, TypeEnumerator type) {
		return legalSectionsService.verifyLegalSections(idUser, type);
	}

}
