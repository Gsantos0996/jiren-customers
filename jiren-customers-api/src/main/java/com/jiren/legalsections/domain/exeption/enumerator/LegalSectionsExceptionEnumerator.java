package com.jiren.legalsections.domain.exeption.enumerator;

import org.springframework.http.HttpStatus;

import com.jiren.shared.exception.ExceptionEnumerator;
import com.jiren.shared.exception.MPlusErrorResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LegalSectionsExceptionEnumerator implements ExceptionEnumerator {

	INVALID_ARGUMENT("legal-400", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("INVALID_ARGUMENT", "%s")),
	LEGAL_SECTIONS_DUPLICATE("legal-400-1", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("LEGAL_SECTIONS_DUPLICATE", "La aceptación de %s se realizó el %s")),
	USER_NOT_FOUND("legal-400-2", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("USER_NOT_FOUND", "No existe el usuario %s")),
	USER_NOT_ACTIVE("legal-400-3", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("USER_NOT_ACTIVE", "El usuario %s no esta activo")),
	CUSTOMER_NOT_FOUND("legal-400-4", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CUSTOMER_NOT_FOUND", "No existe el cliente %s")),
	CUSTOMER_NOT_ACTIVE("legal-400-5", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("CUSTOMER_NOT_ACTIVE", "El cliente %s no se encuentra activo"));
	
    private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;
}
