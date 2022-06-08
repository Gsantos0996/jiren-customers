package com.jiren.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SubWorAreaExceptionEnumerator implements ExceptionEnumerator {

    INVALID_ARGUMENT("subworkarea-400", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("INVALID_ARGUMENT", "%s")),
    NOT_FOUND_SUBWORK_AREA("subworkarea-412-01", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_SUBWORK_AREA", "No existe un subrubro con el código %s"));

	private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;

}
