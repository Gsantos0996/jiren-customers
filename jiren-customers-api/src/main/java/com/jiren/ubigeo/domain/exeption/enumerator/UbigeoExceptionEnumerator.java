package com.jiren.ubigeo.domain.exeption.enumerator;

import org.springframework.http.HttpStatus;

import com.jiren.shared.exception.ExceptionEnumerator;
import com.jiren.shared.exception.MPlusErrorResponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UbigeoExceptionEnumerator implements ExceptionEnumerator {

	INVALID_ARGUMENT("paymenttype-400", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("INVALID_ARGUMENT", "%s")),
    NOT_FOUND_UBIGEO("paymenttype-404-01", HttpStatus.NOT_FOUND, new MPlusErrorResponse("NOT_FOUND_UBIGEO", "Ubigeo no encontrado"));

    private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;
}
