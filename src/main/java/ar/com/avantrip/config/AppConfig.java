package ar.com.avantrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
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
public class AppConfig {

	@Bean
	protected Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("ar.com.avantrip.controller")).paths(PathSelectors.any())
				.build().apiInfo(getApiInfo());

	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
				.contact(new Contact("Daniel Duno", "https://www.avantrip.com", "adbdanieñ@gmail.com"))
				.title("Avantrip").description("Test Avantrip").version("0.0.1")
				.license("2008 - 2019 Avantrip.com - Todos los derechos reservados")
				.licenseUrl("https://www.avantrip.com").build();
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().deepLinking(true).displayOperationId(false).defaultModelsExpandDepth(1)
				.defaultModelExpandDepth(1).defaultModelRendering(ModelRendering.MODEL).displayRequestDuration(true)
				.docExpansion(DocExpansion.FULL).filter(false).maxDisplayedTags(null)
				.operationsSorter(OperationsSorter.ALPHA).showExtensions(true).tagsSorter(TagsSorter.ALPHA)
				.validatorUrl(null).build();
	}

}
