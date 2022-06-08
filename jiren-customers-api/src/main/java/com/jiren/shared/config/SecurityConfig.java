package com.jiren.shared.config;

import com.jiren.customers.infraestructure.CustomerWebSecurityProperties;
import com.jiren.shared.web.filter.ApiKeyRequestHeaderFilter;
import com.jiren.shared.web.util.SkipPathsExceptOneRequestMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

import static com.jiren.shared.security.SecurityConstant.API_KEY_REQUEST_HEADER;
import static com.jiren.shared.security.SecurityConstant.PRINCIPAL_REQUEST_HEADER;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String URL_ENTRY_POINT_PATTERN = "/**";
    private final CustomerWebSecurityProperties webSecurityProperties;
    private final AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(buildPreAuthenticatedAuthenticationProvider());
    }

    protected AuthenticationProvider buildPreAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(this.authenticationUserDetailsService);
        return preAuthenticatedAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        BaseHttpStatelessSecurityConfig
                .forUrlEntryPoint(URL_ENTRY_POINT_PATTERN)
                .accept(http);
        http
                .authorizeRequests()
                .antMatchers(this.webSecurityProperties.getNonAuthenticatedUrls().toArray(String[]::new))
                .anonymous()
                .antMatchers(this.webSecurityProperties.getAuthenticatedUrlPattern())
                .authenticated()
                .and()
                .addFilterBefore(buildApiKeyRequestHeaderFilter(), RequestHeaderAuthenticationFilter.class)
                .addFilterAfter(buildRequestHeaderFilter(this.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

    private RequestHeaderAuthenticationFilter buildRequestHeaderFilter(final AuthenticationManager authenticationManager) {
        RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter = new RequestHeaderAuthenticationFilter();
        requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager);
        requestHeaderAuthenticationFilter.setRequiresAuthenticationRequestMatcher(buildRequestMatcher());
        requestHeaderAuthenticationFilter.setPrincipalRequestHeader(PRINCIPAL_REQUEST_HEADER);

        return requestHeaderAuthenticationFilter;
    }

    private ApiKeyRequestHeaderFilter buildApiKeyRequestHeaderFilter() {
        ApiKeyRequestHeaderFilter apiKeyRequestHeaderFilter = new ApiKeyRequestHeaderFilter();
        apiKeyRequestHeaderFilter.setApiKeyRequestHeader(API_KEY_REQUEST_HEADER);
        return apiKeyRequestHeaderFilter;
    }

    private SkipPathsExceptOneRequestMatcher buildRequestMatcher() {
        return SkipPathsExceptOneRequestMatcher.create(this.webSecurityProperties.getNonAuthenticatedUrls(), this.webSecurityProperties.getAuthenticatedUrlPattern());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        corsConfig.setAllowedMethods(Collections.singletonList(CorsConfiguration.ALL));
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type","X-ClientID","X-AuthorizedUser"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(CorsConfigurationSource corsConfigurationSource) {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE+1);
        return bean;
    }

}