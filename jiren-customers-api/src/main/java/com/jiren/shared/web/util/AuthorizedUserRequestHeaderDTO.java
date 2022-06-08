package com.jiren.shared.web.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthorizedUserRequestHeaderDTO {

    private String customerGuid;
    private String guid;
    private String username;
    private List<String> roles;

}
