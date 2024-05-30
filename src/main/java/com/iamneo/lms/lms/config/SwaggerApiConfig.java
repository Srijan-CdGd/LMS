package com.iamneo.lms.lms.config;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerApiConfig {
    public static final String SWAGGER_INFO_TITLE = "LMS API";
    public static final String SWAGGER_INFO_DESCRIPTION = "This is LMS API documentation";
    public static final String SWAGGER_INFO_VERSION = "1.0.0";
    public static final String SWAGGER_INFO_CONTACT_NAME = "Iamneo";
    public static final String SWAGGER_INFO_CONTACT_EMAIL = "srijan.airon@iamneo.ai";
    // public static final String SWAGGER_INFO_CONTACT_URL = "https://example.com";
    public static final String SWAGGER_INFO_LISENCE_NAME = "Apache 2.0";
    public static final String SWAGGER_INFO_LISENCE_URL = "https://www.apache.org/licenses/LICENSE-2.0.html";

    public static final String JWT_LOCALHOST_URL = "http://localhost:8080";
    public static final String JWT_SECURITY_SCHEME_NAME = "bearerAuth";
    public static final String JWT_SCHEME = "bearer";
    public static final String JWT_DESCRIPTION = "Provide the JWT token.";
    public static final String JWT_BEARER_FORMAT = "JWT";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title(SWAGGER_INFO_TITLE).description(SWAGGER_INFO_DESCRIPTION)
                .version(SWAGGER_INFO_VERSION)
                .contact(new Contact()
                        .name(SWAGGER_INFO_CONTACT_NAME).email(SWAGGER_INFO_CONTACT_EMAIL))
                .license(new License().name(SWAGGER_INFO_LISENCE_NAME).url(SWAGGER_INFO_LISENCE_URL)))
                .servers(List.of(new Server().url(JWT_LOCALHOST_URL)))
                .addSecurityItem(new SecurityRequirement().addList(JWT_SECURITY_SCHEME_NAME))
                .components(new Components().addSecuritySchemes(JWT_SECURITY_SCHEME_NAME,
                        new SecurityScheme().name(JWT_SECURITY_SCHEME_NAME).type(HTTP).scheme(JWT_SCHEME)
                                .description(JWT_DESCRIPTION).bearerFormat(JWT_BEARER_FORMAT)));
    }

}
