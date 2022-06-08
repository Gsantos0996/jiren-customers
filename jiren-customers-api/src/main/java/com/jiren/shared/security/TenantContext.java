package com.jiren.shared.security;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class TenantContext {

    private String tenant;

    public boolean isEmpty() {
        return StringUtils.isBlank(this.tenant);
    }

}
