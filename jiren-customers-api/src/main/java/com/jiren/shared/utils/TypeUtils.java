package com.jiren.shared.utils;

import java.util.Optional;
import java.util.stream.Stream;

import com.jiren.shared.models.types.Type;

public class TypeUtils {
    public static <T> Optional<Type<T>> findByCode(Type<T>[] values, T code) {
        return Stream.of(values)
                .filter(m -> m.getCode().equals(code))
                .findFirst();
    }
    public static <T> boolean exist(Type<T>[] values, T code) {
        return Stream.of(values)
                .filter(m -> m.getCode().equals(code))
                .count() > 0;
    }
    public static <T> Optional<Type<T>> findByDescription(Type<T>[] values, String description) {
        return Stream.of(values)
                .filter(m -> m.getDescription().equalsIgnoreCase(description))
                .findFirst();
    }
}
