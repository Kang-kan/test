package com.xxgame.admin.web.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 公共配置
 * 
 * @author gil
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//为当前包路径
				.apis(RequestHandlerSelectors.basePackage("com.xxgame.admin.web"))
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * ApiInfo
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				// 页面标题
				.title("管理后台API")
				// 创建人
				.contact(new Contact("Gil", "", "149946451@qq.com"))
				// 版本号
				.version("1.0")
				// 描述
				.description("接口文档")
				.build();
	}

}
