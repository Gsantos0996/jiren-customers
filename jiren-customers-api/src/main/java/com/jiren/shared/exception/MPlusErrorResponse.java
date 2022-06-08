package com.jiren.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class MPlusErrorResponse implements Serializable {

    private String code;
    private String message;
}
