package com.jiren.customers.service.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParallelSaveResponse {
    boolean isValid = true;
    List<String> errors = new ArrayList<>();
    CustomerSuccessServiceDTO success;

    public void addError(String error) {
        errors.add(error);
        isValid = false;
    }
}
