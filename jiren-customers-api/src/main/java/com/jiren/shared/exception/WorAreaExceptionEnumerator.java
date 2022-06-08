package com.jiren.shared.exception;

import com.jiren.shared.exception.ExceptionEnumerator;
import com.jiren.shared.exception.MPlusErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WorAreaExceptionEnumerator implements ExceptionEnumerator {

    INVALID_ARGUMENT("workarea-400", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("INVALID_ARGUMENT", "%s")),
	NOT_FOUND_WORK_AREA("workarea-412-01", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_WORK_AREA", "No existe un rubro con el código %s"));

	private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;

}
