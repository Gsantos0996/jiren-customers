package com.jiren.customers.domain.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserEventEnumerator {

    CREATE(1, "Create"),
    UPDATE(2, "Update"),
    DELETE(3, "Delete");

    Integer code;
    String description;
}
