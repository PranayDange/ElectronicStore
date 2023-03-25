package com.lcwd.electronic.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {
    //bean configure karne ke liye hmm configuration class banate hai
    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());


        docket.securityContexts(Arrays.asList(getSecurityContext()));
        docket.securitySchemes(Arrays.asList(getSchemes()));


        ApiSelectorBuilder select = docket.select();
        select.apis(RequestHandlerSelectors.any());
        select.paths(PathSelectors.any());
        Docket build = select.build();
        return build;

    }

    private SecurityContext getSecurityContext() {

        SecurityContext context = SecurityContext
                .builder()
                .securityReferences(getSecurityReferences())
                .build();
        return context;
    }

    private List<SecurityReference> getSecurityReferences() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("Global", "Access EveryThing")
        };
        return Arrays.asList(new SecurityReference("apikey", scopes));
    }


    private ApiKey getSchemes() {
        return new ApiKey("apikey", "Authorization", "header");
    }


    private ApiInfo getApiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Electronic Store Backend API'S",
                "This is backend project for electronic store",
                "1.0.0V",
                "https://www.google.com",
                new Contact("PRanay", "https://www.google.com", "pranaydange04@gmail.com"),
                "License of apis",
                "https://www.google.com",
                new ArrayList<>()

        );
        return apiInfo;
    }
}
