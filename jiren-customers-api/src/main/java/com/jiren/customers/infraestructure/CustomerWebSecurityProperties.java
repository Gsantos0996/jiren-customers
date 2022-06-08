package com.jiren.customers.infraestructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties("customers.web")
public class CustomerWebSecurityProperties {

    private String authorizationCookieName;
    private boolean httpOnly;
    private Integer authorizationCookieAge;//in seconds

    private List<String> nonAuthenticatedUrls;
    private String authenticatedUrlPattern;

}
