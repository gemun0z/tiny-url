package com.mercado.libre.tinyurl.config;

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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.mercado.libre.tinyurl"))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo())
                .tags(new Tag("Methods", ""));
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Tiny URL API")
                .description("")
                .contact(new Contact("Germán Muñoz", "", "german.munoz.soto@gmail.com"))
                .version("1.0.0-SNAPSHOT")
                .build();
    }

}
