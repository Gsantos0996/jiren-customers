package com.jiren.shared.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum GenericExceptionEnumerator implements ExceptionEnumerator {

    UNSUPPORTED_MEDIA_TYPE("benefits-415", HttpStatus.UNSUPPORTED_MEDIA_TYPE, new MPlusErrorResponse("UNSUPPORTED_MEDIA_TYPE", "Formato no soportado")),
    INTERNAL_SERVER_ERROR("benefits-500", HttpStatus.INTERNAL_SERVER_ERROR, new MPlusErrorResponse("INTERNAL_SERVER_ERROR", "Ha ocurrido un error inesperado")),
    DATA_ACCESS_ERROR("benefits-500-2", HttpStatus.INTERNAL_SERVER_ERROR, new MPlusErrorResponse("DATA_ACCESS_ERROR", "Error de base de datos"));

    private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;

    @Override
    public String getInternalCode() {
        return internalCode;
    }

    @Override
    public HttpStatus getHttpCode() {
        return httpCode;
    }

    @Override
    public MPlusErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
