package com.jiren.documents.domain.exception.enumerator;

import com.jiren.shared.exception.ExceptionEnumerator;
import com.jiren.shared.exception.MPlusErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DocumentExceptionEnumerator implements ExceptionEnumerator {

    INVALID_ARGUMENT("documents-400", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("INVALID_ARGUMENT", "%s")),
    DOCUMENT_FILTER_INVALID("documents-400-1", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("DOCUMENT_FILTER_INVALID", "El filtro para obtener documentos no es permitido"));

    private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;

}
