package com.jiren.legalsections.service;

import com.jiren.legalsections.domain.model.type.TypeEnumerator;

public interface LegalSectionsService {
	void createLegalSections(String idUser, TypeEnumerator type);
	Boolean verifyLegalSections(String idUser, TypeEnumerator type);
}
