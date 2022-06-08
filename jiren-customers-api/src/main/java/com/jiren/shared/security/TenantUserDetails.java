package com.jiren.shared.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TenantUserDetails implements UserDetails {

    @Getter
    private final String tenantId;
    private final String username;
    @Getter
    private final String guid;
    private final List<String> roles;

    @Builder
    public TenantUserDetails(String tenantId, String username, String guid, @Singular List<String> roles) {
        this.tenantId = tenantId;
        this.username = username;
        this.guid = guid;
        this.roles = roles;
    }

    private Collection<SimpleGrantedAuthority> authorityCollection;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorityCollection == null) {
            this.authorityCollection = this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return this.authorityCollection;
    }

    @Override
    public String getPassword() {
        return "N/A";
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
