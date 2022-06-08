package com.jiren.shared.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionEnumerator {

    String getInternalCode();

    HttpStatus getHttpCode();

    MPlusErrorResponse getErrorResponse();
}
