package com.jiren.shared.exception;

public class MPlusApiException extends RuntimeException {

    protected final ExceptionEnumerator exceptionEnumerator;
    protected final Object[] messageArgs;

    public MPlusApiException(ExceptionEnumerator exceptionEnumerator, Object... messageArgs) {
        super(String.format(exceptionEnumerator.getErrorResponse().getMessage(), messageArgs));
        this.exceptionEnumerator = exceptionEnumerator;
        this.messageArgs = messageArgs;
    }

    public ExceptionEnumerator getExceptionEnumerator() {
        return this.exceptionEnumerator;
    }

    public Object[] getMessageArgs() {
        return this.messageArgs;
    }
}
