package com.jiren.shared.security;

import com.jiren.customers.domain.exception.enumerator.CustomerExceptionEnumerator;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.web.util.AuthorizedUserRequestHeaderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PreAuthenticatedUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    private static final String SECURITY_ROLE_PREFIX = "ROLE_";
    public static final String AUTHORIZED_USER_SEPARATOR = "\\|";
    public static final String AUTHORIZED_USER_ROLES_SEPARATOR = ",";

    private final Function<String, String> VALIDATE_AND_FORMAT_ROLES = (roleName) -> {
        if (StringUtils.startsWith(roleName, SECURITY_ROLE_PREFIX)) {
            return roleName;
        }
        return SECURITY_ROLE_PREFIX + roleName;
    };

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (TenantContextHolder.getContext().isEmpty()) {
            throw new MPlusApiException(CustomerExceptionEnumerator.API_KEY_NOT_FOUND);
        }
        return createUserDetails(token, TenantContextHolder.getContext().getTenant());
    }

    private TenantUserDetails createUserDetails(PreAuthenticatedAuthenticationToken token, final String tenantId) {
        try {
            AuthorizedUserRequestHeaderDTO dto = buildAuthorizedUserRequestDTO(token.getPrincipal().toString());
            TenantUserDetails.TenantUserDetailsBuilder userBuilder = TenantUserDetails.builder()
                    .tenantId(tenantId)
                    .username(dto.getUsername())
                    .guid(dto.getGuid())
                    .roles(retrieveRoles(dto));
            return userBuilder.build();
        } catch (Exception codeException) {
            log.error("Error parsing header {} thrown with message: {}", codeException.getClass().getSimpleName(), codeException.getMessage(),codeException);
            throw new MPlusApiException(CustomerExceptionEnumerator.INVALID_ARGUMENT, "Formato de cabecera de autorización incorrecto.");
        }
    }

    private AuthorizedUserRequestHeaderDTO buildAuthorizedUserRequestDTO(String authorizedUser){
        String[] values = authorizedUser.split(AUTHORIZED_USER_SEPARATOR);

        return AuthorizedUserRequestHeaderDTO
                .builder()
                .customerGuid(values[0])
                .guid(values[1])
                .username(values[2])
                .roles(Arrays.asList((values[3]).split(AUTHORIZED_USER_ROLES_SEPARATOR)))
                .build();

    }

    private List<String> retrieveRoles(AuthorizedUserRequestHeaderDTO dto) {
        return dto.getRoles().stream()
                .map(VALIDATE_AND_FORMAT_ROLES)
                .collect(Collectors.toList());
    }

}
