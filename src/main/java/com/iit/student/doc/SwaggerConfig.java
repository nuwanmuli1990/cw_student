package com.iit.student.doc;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.service.Parameter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).groupName("Etf Meta Service").groupName("ETF Admin Service").useDefaultResponseMessages(false)
//				.apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//				.paths(PathSelectors.any()).build().directModelSubstitute(Timestamp.class, Date.class);
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("Etf Meta Service Documentation").description("Etf Meta Service REST API Documentation")
//				.version("1.0").build();
//	}
	
	public Docket api(SwaggerConfigProperties swaggerConfigProperties) {
		return new Docket(DocumentationType.SWAGGER_2)
				.host("iit.cloud.com")
				.apiInfo(apiInfo(swaggerConfigProperties))
				.globalOperationParameters(defaultParamaters(swaggerConfigProperties))
				.groupName("Cloud Computing")
				.useDefaultResponseMessages(false)
				.apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build()
				.directModelSubstitute(Timestamp.class, Date.class);
	}
	 
	@Bean
    public Docket eDesignApi(SwaggerConfigProperties swaggerConfigProperties) {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo(swaggerConfigProperties)).globalOperationParameters(defaultParamaters(swaggerConfigProperties))
        		.enable(Boolean.valueOf(swaggerConfigProperties.getEnabled())).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build().pathMapping("/").directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(Boolean.valueOf(swaggerConfigProperties.getUseDefaultResponseMessages()))
                .enableUrlTemplating(Boolean.valueOf(swaggerConfigProperties.getEnableUrlTemplating()));
    }

    @Bean
    UiConfiguration uiConfig(SwaggerConfigProperties swaggerConfigProperties) {
        return UiConfigurationBuilder.builder().deepLinking(Boolean.valueOf(swaggerConfigProperties.getDeepLinking())).displayOperationId(Boolean.valueOf(swaggerConfigProperties.getDisplayOperationId()))
                .defaultModelsExpandDepth(Integer.valueOf(swaggerConfigProperties.getDefaultModelsExpandDepth())).defaultModelExpandDepth(Integer.valueOf(swaggerConfigProperties.getDefaultModelExpandDepth()))
                .defaultModelRendering(ModelRendering.EXAMPLE).displayRequestDuration(Boolean.valueOf(swaggerConfigProperties.getDisplayRequestDuration())).docExpansion(DocExpansion.NONE)
                .filter(Boolean.valueOf(swaggerConfigProperties.getFilter())).maxDisplayedTags(Integer.valueOf(swaggerConfigProperties.getMaxDisplayedTags())).operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(Boolean.valueOf(swaggerConfigProperties.getShowExtensions())).tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS).validatorUrl(null).build();
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Cloud service").description("Cloud service")
				.version("1.0").build();
	}
	
	private ApiInfo apiInfo(SwaggerConfigProperties swaggerConfigProperties) {
        return new ApiInfoBuilder().title(swaggerConfigProperties.getTitle()).description(swaggerConfigProperties.getDescription())
                                   .version(swaggerConfigProperties.getApiVersion()).build();
    }
	
	private List<Parameter> defaultParamaters(SwaggerConfigProperties swaggerConfigProperties) {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Authorization")
                         .modelRef(new ModelRef("String"))
                         .parameterType("header")
                         .defaultValue(swaggerConfigProperties.getDefaultAuthorizationHeader())
                         .required(false)
                         .build();
        java.util.List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameterBuilder.build());
        return parameters;
    }
	

}
