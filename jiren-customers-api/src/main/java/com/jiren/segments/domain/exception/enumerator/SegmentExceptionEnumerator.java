package com.jiren.segments.domain.exception.enumerator;

import com.jiren.shared.exception.ExceptionEnumerator;
import com.jiren.shared.exception.MPlusErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SegmentExceptionEnumerator implements ExceptionEnumerator {

    INVALID_ARGUMENT("jiren-customers-400", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("INVALID_ARGUMENT", "%s")),
	NOT_FOUND_SEGMENT("customer-412-23", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_SEGMENT", "No existe el segmento %s"));

	private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;

}
