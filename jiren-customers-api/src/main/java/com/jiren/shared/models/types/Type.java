package com.jiren.shared.models.types;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Type<ID>{
    @JsonValue
    ID getCode();
    String getDescription();
    String toString();
}
