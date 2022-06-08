package com.jiren.shared.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidArgument {

    private final String fieldName;
    private final String message;
}
