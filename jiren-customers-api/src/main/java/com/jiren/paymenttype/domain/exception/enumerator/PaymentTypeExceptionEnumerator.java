package com.jiren.paymenttype.domain.exception.enumerator;

import com.jiren.shared.exception.ExceptionEnumerator;
import com.jiren.shared.exception.MPlusErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentTypeExceptionEnumerator implements ExceptionEnumerator {

    INVALID_ARGUMENT("paymenttype-400", HttpStatus.BAD_REQUEST, new MPlusErrorResponse("INVALID_ARGUMENT", "%s"));

    private final String internalCode;
    private final HttpStatus httpCode;
    private final MPlusErrorResponse errorResponse;

}
