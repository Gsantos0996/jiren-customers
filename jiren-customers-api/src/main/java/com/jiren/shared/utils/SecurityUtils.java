package com.jiren.shared.utils;

import com.jiren.shared.security.TenantUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static Optional<String> getUserGUID() {
        return getCurrentUser().map(TenantUserDetails::getGuid);
    }

    public static Optional<TenantUserDetails> getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return Optional.empty();
        }
        TenantUserDetails tenantUserDetails = (TenantUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(tenantUserDetails);
    }

}
