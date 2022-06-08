package com.jiren.customers.infraestructure.swagger;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

import static com.jiren.shared.security.SecurityConstant.API_KEY_REQUEST_HEADER;
import static com.jiren.shared.security.SecurityConstant.PRINCIPAL_REQUEST_HEADER;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    public static final String HEADER_PASS_AS = "header";
    public static final String GLOBAL_SCOPE = "global";
    public static final String API_CONTACT_NAME = "Tecnología DiaDía";
    public static final String API_CONTACT_URL = "https://diadia.pe";
    public static final String API_CONTACT_EMAIL = "techonolgy@diadia.pe";
    public static final String API_DESCRIPTION = "API de clientes";
    public static final String API_TITLE = "Jiren Customers";
    public static final String API_VERSION = "1.0.0";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(
                        RequestHandlerSelectors.withClassAnnotation(RestController.class)
                )
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(Arrays.asList(apiKeySecurityScheme(), authorizedUserScheme()));
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact(API_CONTACT_NAME, API_CONTACT_URL, API_CONTACT_EMAIL))
                .description(API_DESCRIPTION)
                .title(API_TITLE)
                .version(API_VERSION)
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(GLOBAL_SCOPE, "Esquema de seguridad por defecto");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference(API_KEY_REQUEST_HEADER, authorizationScopes),new SecurityReference(PRINCIPAL_REQUEST_HEADER, authorizationScopes));
    }

    private SecurityScheme apiKeySecurityScheme() {
        return new ApiKey(API_KEY_REQUEST_HEADER, "xClientId", HEADER_PASS_AS);
    }

    private SecurityScheme authorizedUserScheme() {
        return new ApiKey(PRINCIPAL_REQUEST_HEADER, "xAuthorizedUser", HEADER_PASS_AS);
    }


}
