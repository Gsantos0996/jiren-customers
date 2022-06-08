package com.jiren.legalsections.domain.dao;

import com.jiren.legalsections.domain.model.type.TypeEnumerator;

public interface LegalSectionsDAO {
	void create(String idUser, TypeEnumerator type);
	Boolean verify(String idUser, TypeEnumerator type);
}
