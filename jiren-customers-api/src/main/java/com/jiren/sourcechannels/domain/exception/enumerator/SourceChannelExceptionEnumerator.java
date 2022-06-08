package com.jiren.sourcechannels.domain.exception.enumerator;

import com.jiren.shared.exception.ExceptionEnumerator;
import com.jiren.shared.exception.MPlusErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SourceChannelExceptionEnumerator implements ExceptionEnumerator {

    INVALID_ARGUMENT("sourcechannels-400", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("INVALID_ARGUMENT", "%s")),
    NOT_FOUND_SOURCE_CHANNEL("sourcechannels-412-01", HttpStatus.PRECONDITION_FAILED, new MPlusErrorResponse("NOT_FOUND_SOURCE_CHANNEL", "No existe canal de origen con el código %s"));

	private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;

}
