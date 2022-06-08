package com.jiren.shared.security;

import org.springframework.util.Assert;

public class TenantContextHolder {

    private static final ThreadLocal<TenantContext> contextHolder = new ThreadLocal<>();

    public static void clearContext() {
        contextHolder.remove();
    }

    public static TenantContext getContext() {
        TenantContext context = contextHolder.get();
        if (context == null) {
            context = createEmptyContext();
            contextHolder.set(context);
        }
        return context;
    }

    public static void setContext(TenantContext context) {
        Assert.notNull(context, "Only non-null TenantContext instance are permitted");
        contextHolder.set(context);
    }

    public static TenantContext createEmptyContext() {
        return new TenantContext();
    }

}
