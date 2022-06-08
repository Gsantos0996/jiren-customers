package com.jiren.customers.handler.helper;

import com.jiren.shared.security.TenantContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TenantHelper {

    private static final String AT_TENANT = "@";

    public String buildFullyUsername(final String username) {
        return username + AT_TENANT + TenantContextHolder.getContext().getTenant();
    }

    public String getTenant(){
        return TenantContextHolder.getContext().getTenant();
    }
}
