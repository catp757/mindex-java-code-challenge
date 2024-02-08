package com.mindex.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    public static final String EMPLOYEE_BOOK_TAG = "Employee Service";
    public static final String COMPENSATION_BOOK_TAG = "Compensation Service";

    @Bean
    public Docket api() {
        Tag employeeTag = new Tag(EMPLOYEE_BOOK_TAG, "Operations pertaining to employee management in the Mindex application");
        Tag compensationTag = new Tag(COMPENSATION_BOOK_TAG, "Operations pertaining to compensation management in the Mindex application");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .tags(employeeTag)
                .tags(compensationTag);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Mindex Application API Documentation")
                .description("Midex API")
                .version("1.0")
                .contact(new Contact("Jon Smith", "https://mindex.com", "jondoe@mindex.com"))
                .build();
    }
}
