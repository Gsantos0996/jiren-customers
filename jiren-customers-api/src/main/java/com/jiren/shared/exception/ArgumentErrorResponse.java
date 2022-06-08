package com.jiren.shared.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArgumentErrorResponse {

    private List<InvalidArgument> invalidArguments = new ArrayList<>();
}
