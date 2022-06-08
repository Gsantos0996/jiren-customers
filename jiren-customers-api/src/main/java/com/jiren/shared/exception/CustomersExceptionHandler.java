package com.jiren.shared.exception;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CustomersExceptionHandler {

    private static final int FIRST_NOT_VALID_ARGUMENT = 0;

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<MPlusErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return buildMPlusErrorResponse(new MPlusApiException(GenericExceptionEnumerator.UNSUPPORTED_MEDIA_TYPE));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<MPlusErrorResponse> handleDataAccessException(DataAccessException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return buildMPlusErrorResponse(new MPlusApiException(GenericExceptionEnumerator.DATA_ACCESS_ERROR));
    }

    @ExceptionHandler(value = {Exception.class, Error.class, PreAuthenticatedCredentialsNotFoundException.class})
    public ResponseEntity<MPlusErrorResponse> handleAllExceptions(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return buildMPlusErrorResponse(new MPlusApiException(GenericExceptionEnumerator.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MPlusApiException.class)
    public ResponseEntity<MPlusErrorResponse> handleMPlusException(MPlusApiException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return buildMPlusErrorResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MPlusErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ArgumentErrorResponse error = new ArgumentErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getInvalidArguments().add(new InvalidArgument(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        log.error(ExceptionUtils.getStackTrace(e));
        ExceptionEnumerator exceptionEnumerator = CustomerExceptionEnumerator.INVALID_ARGUMENT;
        MPlusErrorResponse errorResponse = exceptionEnumerator.getErrorResponse();
        errorResponse.setMessage(error.getInvalidArguments().get(FIRST_NOT_VALID_ARGUMENT).getMessage());
        return new ResponseEntity<>(errorResponse, exceptionEnumerator.getHttpCode());
    }

    private ResponseEntity<MPlusErrorResponse> buildMPlusErrorResponse(MPlusApiException e) {
        ExceptionEnumerator exceptionEnumerator = e.getExceptionEnumerator();
        MPlusErrorResponse errorResponse = SerializationUtils.clone(exceptionEnumerator.getErrorResponse());
        String message = e.getMessageArgs() == null ? errorResponse.getMessage() : String.format(errorResponse.getMessage(), e.getMessageArgs());
        errorResponse.setMessage(message);
        return new ResponseEntity<>(errorResponse, e.getExceptionEnumerator().getHttpCode());
    }

}
