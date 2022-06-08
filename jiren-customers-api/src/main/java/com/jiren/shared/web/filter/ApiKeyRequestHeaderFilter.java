package com.jiren.shared.web.filter;

import com.jiren.shared.security.TenantContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ApiKeyRequestHeaderFilter extends GenericFilterBean {

    @Setter
    private String apiKeyRequestHeader = "api-key";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        doExtractApiKey((HttpServletRequest) request);
        chain.doFilter(request, response);
    }

    private void doExtractApiKey(HttpServletRequest request) {
        logger.debug("Retrieving API Key from Request");
        String apiKey = request.getHeader(this.apiKeyRequestHeader);
        if (StringUtils.isNotBlank(apiKey)) {
            TenantContextHolder.getContext().setTenant(apiKey);
        }
    }
}
