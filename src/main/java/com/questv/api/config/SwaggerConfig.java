package com.questv.api.config;

import com.google.common.net.HttpHeaders;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.questv.api"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(getApiInfo())
        .securitySchemes(getSecuritySchemes())
        .securityContexts(getSecurityContexts());
  }

  private List<ApiKey> getSecuritySchemes() {
    return Collections.singletonList(
        new ApiKey("Bearer", HttpHeaders.AUTHORIZATION,
            In.HEADER.name()));
  }

  private List<SecurityContext> getSecurityContexts() {
    return Collections.singletonList(
        SecurityContext
        .builder()
        .securityReferences(getSecurityReferences())
        .forPaths(PathSelectors.ant("/api/**"))
        .build());
  }

  private List<SecurityReference> getSecurityReferences() {
    AuthorizationScope authorizationScope = new AuthorizationScope(
        "global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Collections.singletonList(new SecurityReference("Bearer",
        authorizationScopes));
  }



  private ApiInfo getApiInfo() {
    final Contact gersonSales = new Contact(
        "Gerson Sales",
        "https://gitlab.com/GersonSales",
        "gersonsafj@gmail.com"
    );
    return new ApiInfo("QuesTV Api Documentation",
        "The QuesTV application api.",
        "0.0.1",
        "Terms Of Service URL",
        gersonSales,
        "Licence of the API",
        "Licence URL",
        Collections.emptyList());
  }


}
